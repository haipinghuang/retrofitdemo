package com.retrofit_test.http.exception;

/**
 * Created by 黄海 on 2/11/2017.
 */

public class ClientException extends Exception {
    public ClientException() {
    }

    public ClientException(String message) {
        super(message);
    }

    public ClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public ClientException(Throwable cause) {
        super(cause);
    }

}
