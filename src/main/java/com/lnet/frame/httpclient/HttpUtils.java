package com.lnet.frame.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.function.Function;

public final class HttpUtils {

    private HttpUtils() {
    }

    private static final CloseableHttpClient httpClient = createClient();

    public static CloseableHttpClient createClient() {

        try {
            SSLContextBuilder builder = new SSLContextBuilder();
            builder.loadTrustMaterial(null, new TrustSelfSignedStrategy());
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    builder.build());

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", new PlainConnectionSocketFactory())
                    .register("https", sslsf)
                    .build();

            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
            cm.setMaxTotal(2000);

            return HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .setConnectionManager(cm)
                    .build();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException e) {
            throw new RuntimeException("create http client error", e);
        }


        //return HttpClientBuilder.create().build();
    }


    public static URI combineUri(String uri, List<NameValuePair> parameters) {
        try {
            URIBuilder builder = new URIBuilder(uri);
            if (parameters != null && parameters.size() > 0) builder.addParameters(parameters);
            return builder.build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T postForObject(String uri, HttpEntity body, Function<HttpEntity, T> provider) throws IOException {

        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(body);
        return httpClient.execute(httpPost, ResponseUtils.getHandler(provider));
    }

    public static String postForString(String uri, HttpEntity body) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(body);
        return httpClient.execute(httpPost, ResponseUtils.getHandler(httpEntity -> {
            try {
                return EntityUtils.toString(httpEntity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }

    public static byte[] postForRaw(String uri, HttpEntity body) throws IOException {
        HttpPost httpPost = new HttpPost(uri);
        httpPost.setEntity(body);
        return httpClient.execute(httpPost, ResponseUtils.getHandler(httpEntity -> {
            try {
                return EntityUtils.toByteArray(httpEntity);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }));
    }


    public static String getForString(String uri) {
        return getForString(uri, null);
    }

    public static String getForString(String uri, List<NameValuePair> parameters) {
        try {
            HttpGet httpGet = new HttpGet(combineUri(uri, parameters));
            return httpClient.execute(httpGet, ResponseUtils.getHandler(httpEntity -> {
                try {
                    return EntityUtils.toString(httpEntity);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static <T> T getForObject(String uri, Function<HttpEntity, T> provider) {
        return getForObject(uri, null, provider);
    }

    public static <T> T getForObject(String uri, List<NameValuePair> parameters, Function<HttpEntity, T> provider) {
        try {
            HttpGet httpGet = new HttpGet(combineUri(uri, parameters));
            return httpClient.execute(httpGet, ResponseUtils.getHandler(provider));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
