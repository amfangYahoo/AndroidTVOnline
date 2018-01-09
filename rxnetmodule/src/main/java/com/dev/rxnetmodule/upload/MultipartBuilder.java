package com.dev.rxnetmodule.upload;

import com.dev.rxnetmodule.util.LogUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Created on 2017/12/23 17:44.
 *
 * @author by 王可可
 * @version 1.0
 */

public class MultipartBuilder {

    //当前上传的大小
    private long currentBytesWritten = 0;

    //上一次发送的大小
    private long lastBytesWritten = 0;

    /**
     * 单文件上传构造.
     *
     * @param file 文件
     * @param requestBody 请求体
     * @return MultipartBody
     */
    public static MultipartBody fileToMultipartBody(File file, RequestBody requestBody) {
        MultipartBody.Builder builder = new MultipartBody.Builder();

//        JsonObject jsonObject = new JsonObject();
//        jsonObject.addProperty("fileName", file.getName());
//        jsonObject.addProperty("fileSha", Utils.getFileSha1(file));
//        jsonObject.addProperty("appId", "test0002");

        builder.addFormDataPart("file", file.getName(), requestBody);
        builder.addFormDataPart("type", "9");
        builder.addFormDataPart("token", "be440627b3495b9364bc401c6fa1dd58_m");

//        builder.addFormDataPart("params", jsonObject.toString());
        builder.setType(MultipartBody.FORM);
        return builder.build();
    }

    /**
     * 多文件上传构造.
     *
     * @param files 文件列表
     * @param fileUploadObserver 文件上传回调
     * @return MultipartBody
     */
    public static MultipartBody filesToMultipartBody(List<File> files,
                                                     FileUploadObserver<ResponseBody> fileUploadObserver) {
        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
//        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        for (int i = 0; i < files.size(); i++) {
            File file = files.get(i);
//        for (File file : files) {
            UploadFileRequestBody uploadFileRequestBody =
                    new UploadFileRequestBody(file, fileUploadObserver);
//            JsonObject jsonObject = new JsonObject();

//            jsonObject.addProperty("fileName", file.getName());
//            jsonObject.addProperty("fileSha", Utils.getFileSha1(file));
//            jsonObject.addProperty("appId", "test0002");
//            jsonArray.add(jsonObject);
//            LogUtil.d(jsonObject.toString());
            LogUtils.e("upload","uploadFileRequestBody = "+uploadFileRequestBody);
            builder.addFormDataPart("file"+i, file.getName(), uploadFileRequestBody);
        }

        builder.addFormDataPart("type","9");
        builder.addFormDataPart("token", "5296ef318fe755b5ba129a9d790c52c8_m");
//        builder.addFormDataPart("params", gson.toJson(jsonArray));
        //添加其它信息


//        LogUtil.d(gson.toJson(jsonArray));
        //MediaType.parse("multipart/form-data")

        return builder.build();
    }
}
