package com.dev.rxnetmodule.enity;

/**
 * Created by helin on 2016/10/10 11:44.
 * 实体的基类
 */
public class HttpResultData<T> {

    private int code;
    //用来模仿Data
    private T data;

    private String message;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
