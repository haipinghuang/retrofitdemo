package com.retrofit_test.http.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

/**
 * fileDesc
 * Created by huanghp on 2018/10/11.
 * Email h1132760021@sina.com
 */
public class MyHostNameVerifier implements HostnameVerifier {
    private static final String TAG = "MyHostNameVerifier";

    @Override
    public boolean verify(String hostname, SSLSession session) {
        //没有设置证书的情况下，此方法不被调用，即HostnameVerifier无效
        return hostname.equals(session.getPeerHost());
//        return true;
    }
}
