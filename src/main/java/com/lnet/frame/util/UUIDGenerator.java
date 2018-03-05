package com.lnet.frame.util;

import java.util.UUID;

public class UUIDGenerator implements IdGenerator {

    private UUIDGenerator() {
    }

    @Override
    public String next() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * Thread safe singleton
     */
    private static class Holder {
        static final UUIDGenerator INSTANCE = new UUIDGenerator();
    }

    public static UUIDGenerator getInstance() {
        return Holder.INSTANCE;
    }
}
