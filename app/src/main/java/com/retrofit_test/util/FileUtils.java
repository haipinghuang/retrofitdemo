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
import retrofit2.Response;

/**
 * Created by 黄海 on 2017/4/10.
 */

public class FileUtils {
    public static void response2File(final Response<ResponseBody> response) {

        InputStream inputStream = response.body().byteStream();
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), new Date().getTime() + ".apk");
        byte[] buffer = new byte[2048];
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
