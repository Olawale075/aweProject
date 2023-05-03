package com.codeverse.gnotify.general.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GenericDTOMapper {

    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final static Logger logger = Logger.getLogger(GenericDTOMapper.class.getName());

    static {
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS);
    }

    private GenericDTOMapper() {

    }

    public static <T> T convertJsonBodyToObject(String jsonBody, Class<T> clazz) throws RuntimeException {
        if (StringUtils.isEmpty(jsonBody)) {
            throw new RuntimeException("Received empty response body");
        }
        try {

            return OBJECT_MAPPER.readValue(jsonBody, clazz);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while de-serializing json body", e);
            throw new RuntimeException("Could not read response body", e);

        }
    }

    public static <T> List<T> convertJsonArrayToList(String jsonArray, Class<T> clazz) throws RuntimeException {

        if (StringUtils.isEmpty(jsonArray)) {
            throw new RuntimeException("Received empty response body");
        }

        try {
            JavaType javaType = OBJECT_MAPPER.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
            return OBJECT_MAPPER.readValue(jsonArray, javaType);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while de-serializing json body", e);
            throw new RuntimeException("Could not read response body", e);
        }

    }


    public  static String objToJsonStringMapper(final Object obj)
    {
        try
        {
            return new ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL).findAndRegisterModules().writeValueAsString(obj);
        }
        catch (JsonProcessingException e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
