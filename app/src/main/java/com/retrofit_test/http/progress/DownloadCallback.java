package com.retrofit_test.http.progress;

import android.content.Context;

import com.retrofit_test.http.BaseCallback;
import com.retrofit_test.util.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by 黄海 on 2/18/2017.
 */

public class DownloadCallback extends BaseCallback<ResponseBody> {
    private String fileDir;
    private String fileName;

    public DownloadCallback(Context context, String fileDir, String fileName) {
        super(context);
        this.fileDir = fileDir;
        this.fileName = fileName;
    }

    @Override
    public void onSuccess(retrofit2.Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
        Logger.e(call.request().url().toString(), response.toString());
        boolean flag = save(response.body(), fileDir, fileName);
        Logger.e(fileDir + "/" + fileName + " download " + (flag ? "success" : "failed"));
    }

    /**
     * @param responseBody
     * @param fileDir
     * @param fileName
     * @return true success,others failed;
     */
    private boolean save(ResponseBody responseBody, String fileDir, String fileName) {
        File dir = new File(fileDir);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, fileName);
        long contentLength = responseBody.contentLength();
        InputStream inputStream = responseBody.byteStream();
        FileOutputStream fos = null;
        try {
            int totalRead = 0;
            int len = -1;
            byte[] buffer = new byte[2048];
            fos = new FileOutputStream(file);
            Logger.i("start downloading:" + fileDir + "/" + fileName);
            while ((len = inputStream.read(buffer)) != -1) {
                fos.write(buffer, 0, len);
                totalRead += len;
                onProgress(totalRead, contentLength);
            }
            fos.flush();
            Logger.i("finish downloading:" + fileDir + "/" + fileName);
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * this method is invoked within work thread
     */
    public void onProgress(long totalRead, long contentLength) {
    }


}