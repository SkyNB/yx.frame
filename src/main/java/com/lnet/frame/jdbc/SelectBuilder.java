package com.lnet.frame.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectBuilder implements SqlBuilder {

    private List<String> fields = new ArrayList<>();
    private String fromTable;
    private List<String> orderByFields = new ArrayList<>();
    private String whereFilter;


    public SelectBuilder select(String... fields) {
        this.fields.addAll(Arrays.asList(fields));
        return this;
    }

    public SelectBuilder select(String fields) {
        return select(fields.split(","));
    }


    public SelectBuilder from(String table) {
        this.fromTable = table;
        return this;
    }

    public SelectBuilder orderBy(String orderBy) {
        this.orderByFields.clear();
        this.orderByFields.addAll(Arrays.asList(orderBy.split(",")));
        return this;
    }

    public SelectBuilder where(String... whereFields) {
        this.whereFilter = buildWhereFilter(whereFields);
        return this;
    }


    public String forPaging(long page, int pageSize) {
        return "";
//        return page(build(), page, pageSize);
    }

    public String forCounting() {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT COUNT(*) ");
        builder.append(" FROM ");
        builder.append(fromTable);
        builder.append(buildWhere());
        return builder.toString();
    }


    private String buildWhere() {
        if (whereFilter != null && whereFilter.trim().length() > 0) {
            return " WHERE " + whereFilter;
        }
        return "";
    }

    private String buildOrderByFields() {
        StringBuilder builder = new StringBuilder();
        if (orderByFields.size() > 0) {
            builder.append(" ORDER BY ");
            for (int i = 0; i < orderByFields.size(); i++) {
                if (i > 0) builder.append(", ");
                String field = orderByFields.get(i).toUpperCase().trim();
                builder.append(field);
            }
        }
        return builder.toString();
    }

    private String buildSelectFields() {
        if (fields.size() == 0) return "*";
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fields.size(); i++) {
            if (i > 0) builder.append(", ");
            String field = fields.get(i).toUpperCase();
            builder.append(field);
        }

        return builder.toString();

    }

    public static String buildWhereFilter(String[] whereFields) {
        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < whereFields.length; i++) {
//            if (i > 0) builder.append(" AND ");
//            String field = whereFields[i].toUpperCase();
//            if (hasOperation(field)) builder.append(field);
//            else builder.append(field + " = :" + StringUtils.columnToProperty(field));
//        }
        return builder.toString();
    }


    @Override
    public String build() {
        return null;
    }


    @Override
    public String toString() {
        return build();
    }

}
