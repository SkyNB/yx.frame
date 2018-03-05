package com.lnet.frame.jdbc;

public interface SqlBuilder {
    String build();

     static String sql(){
       return "";
    }
}
