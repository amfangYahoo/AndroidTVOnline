package com.dev.rxnetmodule.enity;

/**
 * 实体的基类
 *
 * 后台数据结构类封装，你的可能是HttpResultData，需要自定义
 */
public class HttpResult<T> {

    private boolean error;
    //用来模仿Data
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}

