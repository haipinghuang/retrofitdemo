package com.retrofit_test.http.https;

import android.content.Context;

import com.retrofit_test.util.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * TrustManager,管理是否验证证书
 * HTTPS 单向认证://blog.csdn.net/ky_heart/article/details/79550863
 * Created by huanghp on 2018/10/11.
 * Email h1132760021@sina.com
 */
public class MyTrustManager {
    /**
     * init TrustManager
     *
     * @param context
     * @param cerFileName 在assets目录下的证书文件名
     * @return
     */
    public static TrustManager[] prepareTrustManager(Context context, String cerFileName) {
        InputStream is = null;
        https:
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            is = context.getResources().getAssets().open(cerFileName);
            Certificate ca = cf.generateCertificate(is);
            Logger.i("CA=" + ((X509Certificate) ca).getSubjectDN());
            // Create a KeyStore containing our trusted CAs
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null, null);
            keyStore.setCertificateEntry("ca", ca);
            // Create a TrustManager that trusts the CAs in our KeyStore
            String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(defaultAlgorithm);
            tmf.init(keyStore);

            return tmf.getTrustManagers();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new TrustManager[0];
    }

    /**
     * get a X509TrustManager
     *
     * @param trustManagers
     * @return
     */
    public static X509TrustManager getSafeTrustManager(TrustManager[] trustManagers) {
        X509TrustManager trustManager = null;
        if (trustManagers != null) {
            for (TrustManager tm : trustManagers) {
                if (tm instanceof X509TrustManager) {
                    trustManager = (X509TrustManager) tm;
                    break;
                }
            }
        }
        return trustManager;
    }
}
