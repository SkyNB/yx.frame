package com.lnet.frame.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectQuery {

    public interface Matcher<T> {
        boolean matches(T target);
    }

    public static <T> List<T> find(Iterable<T> list, Matcher<T> matcher) {
        try {
            List<T> result = new ArrayList<>();

            for (T t : list) {
                if (matcher.matches(t)) result.add(t);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> find(Iterable<T> list, final Map<String, Object> filter) {
        try {
            Matcher<T> matcher = getFilter(filter);
            return find(list, matcher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> List<T> find(Iterable<T> list, String propName, Object propValue) {
        try {

            Map<String, Object> filter = new HashMap<>();
            filter.put(propName, propValue);
            return find(list, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T findOne(Iterable<T> list, Matcher<T> matcher) {
        try {
            for (T t : list) {
                if (matcher.matches(t)) return t;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T findOne(Iterable<T> list, String propName, Object propValue) {
        try {
            Map<String, Object> filter = new HashMap<>();
            filter.put(propName, propValue);
            return findOne(list, filter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static <T> T findOne(Iterable<T> list, final Map<String, Object> filter) {
        try {
            Matcher<T> matcher = getFilter(filter);
            return findOne(list, matcher);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static <T> Matcher<T> getFilter(final Map<String, Object> filter) {
        return new Matcher<T>() {
            @Override
            public boolean matches(T target) {
                boolean flag = true;
                for (Map.Entry<String, Object> entry : filter.entrySet()) {
                    Object fieldValue = ReflectUtils.getProperty(target, entry.getKey());
                    if (!entry.getValue().equals(fieldValue)) {
                        flag = false;
                        break;
                    }
                }
                return flag;
            }
        };
    }

}
