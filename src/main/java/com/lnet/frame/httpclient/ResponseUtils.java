package com.lnet.frame.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.util.EntityUtils;

import java.util.function.Consumer;
import java.util.function.Function;

public final class ResponseUtils {

    private ResponseUtils() {
    }

    public static <T> T read(HttpResponse response, Function<HttpEntity, T> provider) {
        StatusLine statusLine = response.getStatusLine();
        int status = statusLine.getStatusCode();
        if (status >= 200 && status < 300) {
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                throw new RuntimeException("Response contains no content");
            }
            try {
                return provider.apply(entity);
            } finally {
                EntityUtils.consumeQuietly(entity);
            }
        } else {
            throw new RuntimeException(String.format("%s : %s", statusLine.getStatusCode(), statusLine.getReasonPhrase()));
        }
    }

    public static void consume(HttpResponse response, Consumer<HttpEntity> consumer) throws ClientProtocolException {
        read(response, (httpEntity) -> {
            consumer.accept(httpEntity);
            return null;
        });
    }


    public static <T> ResponseHandler<T> getHandler(Function<HttpEntity, T> provider) {
        return response -> read(response, provider);
    }
}
