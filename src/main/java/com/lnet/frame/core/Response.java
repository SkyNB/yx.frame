package com.lnet.frame.core;

import java.io.Serializable;

public class Response<T> implements Serializable {

    private T body;
    private boolean success;
    private String message;

    // region constructor
    public Response(T body, boolean success, String message) {
        this.body = body;
        this.success = success;
        this.message = message;
    }

    public Response() {
        this(null, true, "");
    }

    public Response(T body) {
        this(body, true, "");
    }
    // endregion

    // region getter and setter
    public T getBody() {
        return body;
    }

    public Response<T> setBody(T body) {
        this.body = body;
        return this;
    }

    public boolean isSuccess() {
        return success;
    }

    public Response<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public Response<T> setMessage(String message) {
        this.message = message;
        return this;
    }
    // endregion
}
