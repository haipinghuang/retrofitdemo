package com.retrofit_test.http.progress;

import java.io.File;

/**
 * DownloadListener
 */
public interface DownloadListener {
    void onStart();

    void onProgress(int totollength, int downloadedLength);

    void onSuccess(String uri, String fileName, File dstDir);

    void onFailure(String errorMsg);

    class SimpleDownloadListener implements DownloadListener {

        @Override
        public void onStart() {

        }

        @Override
        public void onProgress(int totollength, int downloadedLength) {

        }

        @Override
        public void onSuccess(String uri, String fileName, File dstDir) {

        }

        @Override
        public void onFailure(String errorMsg) {

        }
    }
}