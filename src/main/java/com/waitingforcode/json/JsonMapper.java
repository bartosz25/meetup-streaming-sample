package com.waitingforcode.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public final class JsonMapper {

    private static final ObjectMapper MAPPER;
    static {
        MAPPER = new ObjectMapper();
        MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getMapper() {
        return MAPPER;
    }

}
