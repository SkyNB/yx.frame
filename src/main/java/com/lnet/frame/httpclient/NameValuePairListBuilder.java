package com.lnet.frame.httpclient;

import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class NameValuePairListBuilder {

    public static NameValuePairListBuilder builder(){
        return new NameValuePairListBuilder();
    }

    private final List<NameValuePair> nameValuePairs;

    private NameValuePairListBuilder() {
        this.nameValuePairs = new ArrayList<>();
    }

    public NameValuePairListBuilder with(String name, String value) {
        this.nameValuePairs.add(new BasicNameValuePair(name, value));
        return this;
    }

    public NameValuePairListBuilder with(BasicNameValuePair nvp) {
        this.nameValuePairs.add(nvp);
        return this;
    }

    public NameValuePairListBuilder with(List<NameValuePair> nvps) {
        this.nameValuePairs.addAll(nvps);
        return this;
    }

    public NameValuePairListBuilder sort() {
        Collections.sort(this.nameValuePairs, (a, b) -> a.getName().compareTo(b.getName()));
        return this;
    }

    public List<NameValuePair> build() {
        return this.nameValuePairs;
    }

    public UrlEncodedFormEntity buildFormEntity() {
        return new UrlEncodedFormEntity(this.nameValuePairs, Consts.UTF_8);
    }
}
