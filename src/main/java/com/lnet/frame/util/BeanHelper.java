package com.lnet.frame.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public final class BeanHelper {

    public static Map<String, Object> describe(Object source) {

        Class sourceClass = source.getClass();
        if (Map.class.isAssignableFrom(sourceClass)) {
            Map<String, Object> result = new HashMap<>();
            result.putAll((Map) source);
            return result;
        } else {
            try {
                return PropertyUtils.describe(source);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ignore) {
                return Collections.emptyMap();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T convert(Object source, Class<T> targetClass) {
        T target = null;
        try {
            if (Map.class.isAssignableFrom(targetClass)) {
                target = (T) describe(source);
            } else {
                target = targetClass.newInstance();
                BeanUtils.copyProperties(target, source);
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException ignore) {
        }

        return target;
    }


    public static <T> List<T> convertList(List source, Class<T> clazz) {

        Objects.requireNonNull(source);
        List<T> result = new ArrayList<T>(source.size());

        source.forEach(item -> {
            result.add(convert(item, clazz));
        });

        return result;
    }

}