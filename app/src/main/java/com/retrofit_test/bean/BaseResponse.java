package com.retrofit_test.bean;

public class BaseResponse<T> {

    private boolean result;
    private int resultCode;
    private String msg;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(boolean result, int resultCode, String msg) {
        this.result = result;
        this.resultCode = resultCode;
        this.msg = msg;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "result=" + result +
                ", resultCode=" + resultCode +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
