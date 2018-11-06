package com.retrofit_test.util;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import okhttp3.ResponseBody;

/**
 * Created by 黄海 on 2017/4/10.
 */

public class FileUtils {
    public static void response2File(final ResponseBody responseBody) {
        InputStream inputStream = responseBody.byteStream();
        long fileLength = responseBody.contentLength();
        if (fileLength <= 0) return;

        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), new Date().getTime() + ".apk");
        byte[] buffer = new byte[8192];
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            int length = -1;
            while ((length = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, length);
            }
            fos.flush();
            Log.e("hhp", file.getName() + "下载成功=" + file.length());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
