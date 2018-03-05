package com.lnet.frame.core;

/**
 * Created by Administrator on 2016/9/22.
 */

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

public class EnumConverter implements Converter {
    @Override
    public <T> T convert(Class<T> tClass, Object o) {
        String enumValName = (String) o;
        Enum[] enumConstants = (Enum[]) tClass.getEnumConstants();

        for (Enum enumConstant : enumConstants) {
            if (enumConstant.name().equals(enumValName)) {
                return (T) enumConstant;
            }
        }

        throw new ConversionException(String.format("Failed to converter %s value to %s class", enumValName, tClass.toString()));
    }
}
