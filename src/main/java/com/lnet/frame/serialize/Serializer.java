package com.lnet.frame.serialize;

import java.io.InputStream;
import java.io.OutputStream;

public interface Serializer {

    byte[] serialize(Object source);
    void serialize(Object source, OutputStream outputStream);

    Object deserialize(InputStream inputStream);
    <T> T deserialize(InputStream inputStream, Class<T> clazz);

    Object deserialize(byte[] source);
    <T> T deserialize(byte[] source, Class<T> clazz);

}
