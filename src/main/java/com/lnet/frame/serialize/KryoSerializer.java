package com.lnet.frame.serialize;

import java.io.InputStream;
import java.io.OutputStream;

public class KryoSerializer implements Serializer {
    @Override
    public byte[] serialize(Object source) {
        return new byte[0];
    }

    @Override
    public void serialize(Object source, OutputStream outputStream) {

    }

    @Override
    public Object deserialize(InputStream inputStream) {
        return null;
    }

    @Override
    public <T> T deserialize(InputStream inputStream, Class<T> clazz) {
        return null;
    }

    @Override
    public Object deserialize(byte[] source) {
        return null;
    }

    @Override
    public <T> T deserialize(byte[] source, Class<T> clazz) {
        return null;
    }
}
