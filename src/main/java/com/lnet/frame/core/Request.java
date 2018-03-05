package com.lnet.frame.core;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class Request<T> implements Serializable {

    private String invoker;
    private final Map<String, Object> header;
    private final Instant timestamp;

    private T body;

    public Request() {
        this.header = new HashMap<>();
        this.timestamp = Instant.now();
    }

    public Request(String invoker, T body) {
        this();
        this.invoker = invoker;
        this.body = body;
    }

    public String getInvoker() {
        return invoker;
    }

    public Map<String, Object> getHeader() {
        return header;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public T getBody() {
        return body;
    }

    public Request<T> setInvoker(String invoker) {
        this.invoker = invoker;
        return this;
    }

    public Request<T> setBody(T body) {
        this.body = body;
        return this;
    }

    public Request<T> setHeader(String key, String value) {
        this.header.put(key, value);
        return this;
    }

    public <E> Request<E> derive(E body) {
        return new Request<>(this.invoker, body);
    }

    public <E> Request<E> derive(Supplier<E> supplier) {
        return new Request<>(this.invoker, supplier.get());
    }

}
