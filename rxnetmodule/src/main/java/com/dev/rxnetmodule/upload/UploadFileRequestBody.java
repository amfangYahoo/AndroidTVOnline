package com.dev.rxnetmodule.upload;

import com.dev.rxnetmodule.util.LogUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created on 2017/12/23 17:45.
 *
 * @author by 王可可
 * @version 1.0
 */

public class UploadFileRequestBody extends RequestBody {

    private RequestBody mRequestBody;
    private FileUploadObserver<ResponseBody> fileUploadObserver;

    public UploadFileRequestBody(File file, FileUploadObserver<ResponseBody> fileUploadObserver) {
        this.mRequestBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
//        this.mRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
        this.fileUploadObserver = fileUploadObserver;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {

        if (sink instanceof Buffer) {
            // Log Interceptor
            mRequestBody.writeTo(sink);
            return;
        }

        CountingSink countingSink = new CountingSink(sink);
        BufferedSink bufferedSink = Okio.buffer(countingSink);
        // 写入
        mRequestBody.writeTo(bufferedSink);
        // 刷新
        // 必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();

    }

    /**
     * CountingSink.
     */
    protected final class CountingSink extends ForwardingSink {

        private long bytesWritten = 0;

        public CountingSink(Sink delegate) {
            super(delegate);
        }

        @Override
        public void write(Buffer source, long byteCount) throws IOException {
            super.write(source, byteCount);

            bytesWritten += byteCount;
//            LogUtils.e("upload","bytesWritten == >"+bytesWritten);
            if (fileUploadObserver != null) {
                fileUploadObserver.onProgressChange(bytesWritten, contentLength());
            }

        }

    }

}
