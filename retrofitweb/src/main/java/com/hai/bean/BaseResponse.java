package com.hai.bean;

public class BaseResponse<T> {
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_FAILED = 500;
    private String msg;
    private int code;
    private T data;

    public BaseResponse() {
    }

    public BaseResponse(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    public BaseResponse(int code, T data) {
        this.code = code;
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

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

    @Override
    public String toString() {
        return "BaseResponse{" +
                "msg='" + msg + '\'' +
                ", code=" + code +
                ", data=" + data +
                '}';
    }
}
