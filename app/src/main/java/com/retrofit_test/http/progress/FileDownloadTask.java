package com.retrofit_test.http.progress;

import android.app.NotificationManager;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import com.retrofit_test.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by 黄海 on 2017/7/14.
 */
public class FileDownloadTask extends Thread implements DownloadListener {
    private static final String TAG = "FileDownloadTask";
    private static AtomicInteger num = new AtomicInteger(0);
    private String uri;
    private String fileName;
    private File cacheDir;
    private Context context;
    private String notifyTitle;

    private DownloadListener downloadListener;
    private NotificationManager notifyManager;
    private volatile int tempId;
    private NotificationCompat.Builder builder;
    private int lastProgress;

    /**
     * 不显示通知进度
     *
     * @param uri
     * @param fileName
     * @param cacheDir
     */
    public FileDownloadTask(@NonNull String uri, @NonNull String fileName, @NonNull File cacheDir) {
        this(uri, fileName, cacheDir, null, null);
    }

    /**
     * 展示通知进度
     *
     * @param uri
     * @param fileName
     * @param cacheDir
     * @param context
     * @param notifyTitle
     */
    public FileDownloadTask(@NonNull String uri, @NonNull String fileName, @NonNull File cacheDir, @Nullable Context context, @Nullable String notifyTitle) {
        this.uri = uri;
        this.fileName = fileName;
        this.cacheDir = cacheDir;
        this.context = context;
        this.notifyTitle = notifyTitle;
    }

    @Override
    public void run() {
        onStart();
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        FileOutputStream fos = null;
        try {
            URL uri = new URL(this.uri);
            httpURLConnection = (HttpURLConnection) uri.openConnection();
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setReadTimeout(15000);
            //防止Content Body将用Chunked编码传输内容，code=200 length=-1,
            httpURLConnection.setRequestProperty("Accept-Encoding", "identity");
            int contentLength = httpURLConnection.getContentLength();
            if (httpURLConnection.getResponseCode() != 404 && contentLength > 0) {
                inputStream = httpURLConnection.getInputStream();
                fos = new FileOutputStream(new File(cacheDir, fileName));
                byte[] buffer = new byte[1024 * 8];
                int length = -1, downloadedLength = 0;
                while ((length = inputStream.read(buffer)) != -1) {
                    fos.write(buffer, 0, length);
                    downloadedLength += length;
                    onProgress(contentLength, downloadedLength);
                }
                onSuccess(this.uri, this.fileName, this.cacheDir);
            } else
                onFailure("status=" + httpURLConnection.getResponseCode() + " and contentLength=" + contentLength);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            onFailure(null);
        } catch (IOException e) {
            e.printStackTrace();
            onFailure(null);
        } finally {
            if (httpURLConnection != null) httpURLConnection.disconnect();
            if (inputStream != null) try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (fos != null) try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStart() {
        Log.i(TAG, "onStart() called with: uri = [" + uri + "], fileName = [" + fileName + "]");
        if (downloadListener != null) downloadListener.onStart();
        if (context != null && !TextUtils.isEmpty(notifyTitle)) {//说明要通知栏显示下载进度
            notifyManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            builder = new NotificationCompat.Builder(context);
            builder.setSmallIcon(R.mipmap.ic_launcher).setTicker("开始下载" + notifyTitle).setContentTitle("下载通知").setContentText("开始下载" + notifyTitle).setAutoCancel(true);
            notifyManager.notify(tempId = num.getAndIncrement(), builder.build());
        }
    }

    @Override
    public void onProgress(int totollength, int downloadedLength) {
        if (downloadListener != null)
            downloadListener.onProgress(totollength, downloadedLength);
        if (context != null && !TextUtils.isEmpty(notifyTitle)) {//说明要通知栏显示下载进度
            int progress = (100 * downloadedLength / totollength);
            if (progress != lastProgress) {//频繁更新会导致主界面卡顿
                builder.setContentText("正在下载 " + notifyTitle + " " + progress + " % ").setProgress(100, progress, false).setOngoing(true);
                notifyManager.notify(tempId, builder.build());
                lastProgress = progress;
            }
        }
    }

    @Override
    public void onSuccess(String uri, String fileName, File dstDir) {
        Log.i(TAG, "onSuccess() called with: uri = [" + uri + "], fileName = [" + fileName + "], dstDir = [" + dstDir.getAbsolutePath() + "]");
        if (context != null && !TextUtils.isEmpty(notifyTitle)) notifyManager.cancel(tempId);

        if (downloadListener != null)
            downloadListener.onSuccess(uri, fileName, dstDir);
    }

    @Override
    public void onFailure(String errorMsg) {
        Log.e(TAG, "onFailure() called with: uri = [" + uri + "], fileName = [" + fileName + "] and errorMsg: " + errorMsg);
        File tfile = new File(cacheDir, fileName);
        if (tfile != null) tfile.delete();//删除下载出错的文件
        if (context != null && !TextUtils.isEmpty(notifyTitle)) {
//            builder.setContentText(notifyTitle + " 下载失败").setAutoCancel(true).setOngoing(false).setContentIntent(null).setContentIntent(PendingIntent.getActivity(context, 0, new Intent(), 0));
            builder.setContentText(notifyTitle + " 下载失败").setAutoCancel(true).setOngoing(false);
            notifyManager.notify(tempId, builder.build());
        }
        if (downloadListener != null)
            downloadListener.onFailure(errorMsg);
    }

    public void setDownloadListener(DownloadListener downloadListener) {
        this.downloadListener = downloadListener;
    }
}
