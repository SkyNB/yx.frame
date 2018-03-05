package com.lnet.frame.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class CsDateDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        String dateText = jsonParser.getText();
        if (dateText != null && dateText.length() > 0) {
            try {
                long timestamp = Long.valueOf(dateText.replaceAll("[^0-9]", ""));
                ZoneId zoneId = deserializationContext.getTimeZone() == null ? ZoneId.systemDefault() : deserializationContext.getTimeZone().toZoneId();
                return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), zoneId);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
