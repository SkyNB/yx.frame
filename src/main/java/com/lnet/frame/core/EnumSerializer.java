package com.lnet.frame.core;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.commons.beanutils.PropertyUtils;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class EnumSerializer extends JsonSerializer {


    @Override
    public void serialize(Object o, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeFieldName("name");
        generator.writeNumber("\"" + o.toString() + "\"");
        generator.writeFieldName("value");
        generator.writeNumber("\"" + o.toString() + "\"");
        generator.writeFieldName("text");
        generator.writeString(getProperty(o, "text").toString());
        generator.writeEndObject();
    }


    public Object getProperty(Object source, String propName) {
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
}