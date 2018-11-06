package com.retrofit_test.http.progress;

import com.retrofit_test.http.SimpleOkHttpCallBack;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * 上传文件进度回调
 * Created by 黄海 on 2017/2/17.
 */
public class UploadRequestBody extends RequestBody {
    private SimpleOkHttpCallBack callBack;
    private RequestBody delegate;

    public UploadRequestBody(RequestBody delegate, SimpleOkHttpCallBack callBack) {
        this.callBack = callBack;
        this.delegate = delegate;
    }

    @Override
    public MediaType contentType() {
        return delegate.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return delegate.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        BufferedSink buffer = Okio.buffer(new ProgressSink(sink));
        delegate.writeTo(buffer);
        buffer.flush();
    }


    protected final class ProgressSink extends ForwardingSink {
        private long bytesWritten = 0;

        public ProgressSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);
            bytesWritten += byteCount;
            callBack.onProgress(bytesWritten, contentLength());
        }
    }
}