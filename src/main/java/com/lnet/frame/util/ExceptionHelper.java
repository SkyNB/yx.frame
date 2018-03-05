package com.lnet.frame.util;

import java.util.ArrayList;
import java.util.List;

public final class ExceptionHelper {

    public static List<String> getExceptionMessageChain(Throwable throwable) {
        List<String> result = new ArrayList<>();
        while (throwable != null) {
            result.add(throwable.getMessage());
            throwable = throwable.getCause();
        }
        return result;
    }

    public static String getJoinedExceptionMessageChain(Throwable throwable, String dismiter) {
        return String.join(dismiter, getExceptionMessageChain(throwable));
    }

    public static String getJoinedExceptionMessageChain(Throwable throwable) {
        return String.join(" --> ", getExceptionMessageChain(throwable));
    }

    public static Throwable getRootCause(Throwable throwable) {
        Throwable root = throwable;
        while (root.getCause() != null) {
            root = root.getCause();
        }
        return root;
    }

    public static String getRootCauseMessage(Throwable throwable) {
        return getRootCause(throwable).getMessage();
    }


}
