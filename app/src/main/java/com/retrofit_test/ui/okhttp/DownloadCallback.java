package com.retrofit_test.ui.okhttp;

import com.retrofit_test.util.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DownloadCallback implements Callback {
    private String fileDir;
    private String fileName;

    public DownloadCallback(String fileDir, String fileName) {
        this.fileDir = fileDir;
        this.fileName = fileName;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Logger.e(call.toString(), e.getLocalizedMessage());
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        Logger.e(call.request().url().toString(), response.toString());
        if (response.isSuccessful()) {
            boolean flag = save(response, fileDir, fileName);
            Logger.e(fileDir + "/" + fileName + " download " + (flag ? "success" : "failed"));
        }
    }

    /**
     * @param response
     * @param fileDir
     * @param fileName
     * @return true success,others failed;
     */
    private boolean save(Response response, String fileDir, String fileName) {
        File dir = new File(fileDir);
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, fileName);
        long contentLength = response.body().contentLength();
        InputStream inputStream = response.body().byteStream();
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

    public void onProgress(long totalRead, long contentLength) {
    }

}