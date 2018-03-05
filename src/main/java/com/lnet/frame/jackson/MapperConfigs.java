package com.lnet.frame.jackson;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public final class MapperConfigs {

    public final static Map<DeserializationFeature, Boolean> DESERIALIZATION_FEATURES;
    public final static Map<SerializationFeature, Boolean> SERIALIZATION_FEATURES;
    public final static Map<MapperFeature, Boolean> MAPPER_FEATURES;
    public final static String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    static {

        Map<DeserializationFeature, Boolean> deserializationFeatures = new HashMap<>();
        deserializationFeatures.put(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        deserializationFeatures.put(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
        deserializationFeatures.put(DeserializationFeature.WRAP_EXCEPTIONS, true);
        DESERIALIZATION_FEATURES = Collections.unmodifiableMap(deserializationFeatures);


        Map<SerializationFeature, Boolean> serializationFeatures = new HashMap<>();
        serializationFeatures.put(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        serializationFeatures.put(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        serializationFeatures.put(SerializationFeature.WRAP_EXCEPTIONS, true);
        serializationFeatures.put(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        SERIALIZATION_FEATURES = Collections.unmodifiableMap(serializationFeatures);

        Map<MapperFeature, Boolean> mapperFeatures = new HashMap<>();
        mapperFeatures.put(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
        MAPPER_FEATURES = Collections.unmodifiableMap(mapperFeatures);
    }

}
