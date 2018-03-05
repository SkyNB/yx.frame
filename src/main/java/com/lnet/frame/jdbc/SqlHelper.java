package com.lnet.frame.jdbc;

public class SqlHelper {

    public static String countByColumn(String table, String column, Object value){
        return count(table, String.format("%s = %s", column, value));
    }

    public static String count(String table, String where){
        return String.format("SELECT COUNT (*) FROM %s WHERE %s", table, where);
    }
}
