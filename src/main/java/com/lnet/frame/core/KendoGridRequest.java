package com.lnet.frame.core;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class KendoGridRequest implements Serializable{

    public static final int MAX_RECORD = 10000;

    private Integer page;
    private Integer pageSize;
    private Integer take;
    private Integer skip;
    private List<Sort> sort;
    private List<GroupDescriptor> group;
    private List<AggregateDescriptor> aggregate;
    private HashMap<String, Object> data;

    private Filter filter;

    public HashMap<String, Object> getData() {
        return data;
    }

    public KendoGridRequest() {
        filter = new Filter();
        data = new HashMap<>();
    }

    @JsonAnySetter
    public void handleUnknown(String key, Object value) {
        if (data == null) data = new HashMap<>();
        data.put(key, value);
    }

    public Integer getPage() {
        return page == null ? 1 : page;
    }

    public Integer getPageSize() {
        return pageSize == null ? MAX_RECORD : pageSize;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor

    public static class Filter implements Serializable{
        private String logic;
        private String name;
        private Object value;
        private List<Filter> filters;

        public Filter(String name, Object value) {
            this.name = name;
            this.value = value;
        }
    }

    public void setParams(String key, Object value) {
        if (filter == null) {
            filter = new Filter();
        }
        if (filter.getFilters() == null) {
            filter.setFilters(new ArrayList<>());
        }
        filter.getFilters().add(new Filter(key, value));
    }

    public Map<String, Object> getParams() {
        Map<String, Object> result = new HashMap<>();
        if (filter != null && filter.getFilters() != null) {
            filter.getFilters().stream().filter(f -> null!=f.getValue()&&!StringUtils.isEmpty(f.getValue().toString())).forEach(f ->
                    result.put(f.getName(), f.getValue()));
        }
        if (sort != null && sort.size() > 0) {
            List<String> orders = new ArrayList<>();
            for (Sort od : sort) {
                String column = convertToColumnName(od.getField()).replaceAll(".TEXT", "");
                orders.add(column.concat(" ").concat(od.dir));
            }
            result.put("orderBy", String.join(",", orders));
        }
        return result;
    }

    public void addOrder(String field, String dir) {
        if (this.getSort() == null) {
            this.setSort(new ArrayList<>());
        }
        this.getSort().add(new Sort(field, dir));
    }

    @Data
    public static class Sort implements Serializable{
        private String field;
        private String dir;

        public Sort() {
        }

        public Sort(String field, String dir) {
            this.field = field;
            this.dir = dir;
        }

    }

    public static class GroupDescriptor extends Sort {
        private List<AggregateDescriptor> aggregates;

        public GroupDescriptor() {
            aggregates = new ArrayList<>();
        }

        public List<AggregateDescriptor> getAggregates() {
            return aggregates;
        }
    }

    public static class AggregateDescriptor implements Serializable{
        private String field;
        private String aggregate;

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public String getAggregate() {
            return aggregate;
        }

        public void setAggregate(String aggregate) {
            this.aggregate = aggregate;
        }
    }

    public static class FilterDescriptor implements Serializable{
        private String logic;
        private List<FilterDescriptor> filters;
        private String field;
        private Object value;
        private String operator;
        private boolean ignoreCase = true;

        public FilterDescriptor() {
            filters = new ArrayList<>();
        }

        public FilterDescriptor(String logic, String field, String operator, Object value, boolean ignoreCase) {
            this.logic = logic;
            this.field = field;
            this.operator = operator;
            this.value = value;
            this.ignoreCase = ignoreCase;
            this.filters = new ArrayList<>();
        }

        public boolean exists(String name) {
            for (FilterDescriptor filter : filters) {
                if (filter.field.equals(name)) return true;
            }
            return false;
        }

        /**
         * @param field    字段名
         * @param operator 关系（'eq','neq','startswith','contains','doesnotcontain','endswith'）
         * @param value    值
         */
        public FilterDescriptor(String field, String operator, Object value) {
            this.field = field;
            this.operator = operator;
            this.value = value;
            this.filters = new ArrayList<FilterDescriptor>();
        }


        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getLogic() {
            return logic;
        }

        public void setLogic(String logic) {
            this.logic = logic;
        }

        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        public void setIgnoreCase(boolean ignoreCase) {
            this.ignoreCase = ignoreCase;
        }

        public List<FilterDescriptor> getFilters() {
            return filters;
        }
    }

    public static String convertToColumnName(String propName) {
        return String.join("_", splitCamelCaseString(propName)).toUpperCase();
    }

    public static List<String> splitCamelCaseString(String s) {
        List<String> result = new ArrayList<String>();

        if (StringUtils.isEmpty(s)) return result;

        StringBuilder word = new StringBuilder();
        char[] buff = s.toCharArray();
        boolean prevIsUpper = false;

        for (int i = 0; i < buff.length; i++) {
            char ch = buff[i];
            if (Character.isUpperCase(ch)) {
                if (i == 0) {
                    word.append(ch);
                } else if (!prevIsUpper) {
                    result.add(word.toString());
                    word = new StringBuilder();
                    word.append(ch);
                } else {
                    word.append(ch);
                }
                prevIsUpper = true;
            } else {
                word.append(ch);
                prevIsUpper = false;
            }
        }

        if (word != null && word.length() > 0) result.add(word.toString());

        return result;
    }

}
