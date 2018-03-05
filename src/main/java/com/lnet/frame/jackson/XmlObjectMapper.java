package com.lnet.frame.jackson;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.text.SimpleDateFormat;

public class XmlObjectMapper extends XmlMapper {

    public XmlObjectMapper() {
        MapperConfigs.DESERIALIZATION_FEATURES.forEach(this::configure);
        MapperConfigs.SERIALIZATION_FEATURES.forEach(this::configure);
        MapperConfigs.MAPPER_FEATURES.forEach(this::configure);

        setDateFormat(new SimpleDateFormat(MapperConfigs.DATE_FORMAT));
        findAndRegisterModules();
    }

    public void setDateFormatPattern(String pattern) {
        this.setDateFormat(new SimpleDateFormat(pattern));
    }
}
