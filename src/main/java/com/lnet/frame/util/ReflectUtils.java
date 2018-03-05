package com.lnet.frame.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

public class ReflectUtils {

    public static Object getProperty(Object source, String propName) {
        try {
            return PropertyUtils.getProperty(source, propName);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setProperty(Object target, String name, Object value) {
        try {
            PropertyUtils.setProperty(target, name, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }


    public static Object convert(Object source, Class targetClass) {
        Object target = null;
        try {
            target = targetClass.newInstance();
            BeanUtils.copyProperties(target, source);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return target;

    }

    public static void copyCollection(Collection destList, Class destClass, Collection orgList) {
        try {
            for (Object o : orgList) {
                Object dest = destClass.newInstance();
                BeanUtils.copyProperties(dest, o);
                destList.add(dest);
            }
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
