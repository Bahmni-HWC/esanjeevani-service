package org.bahmnihwc.esanjeevaniservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bahmnihwc.esanjeevaniservice.constants.MessageCodes;

import java.util.Map;
import java.util.Objects;

public class ESanjeevaniResponseUtil {

    public static boolean isSuccessResponse(String response) throws Exception {
        String messageCode = getMessageCodeField(response);
        return messageCode.equals(MessageCodes.SUCCESS);
    }

    public static String getMessageField(String response) throws JsonProcessingException {
        Map<String, Object> jsonResponse = new ObjectMapper().readValue(response, Map.class);
        return jsonResponse.getOrDefault("message","").toString();
    }

    public static String getMessageCodeField(String response) throws JsonProcessingException {
        Map<String, Object> jsonResponse = new ObjectMapper().readValue(response, Map.class);
        return jsonResponse.getOrDefault("msgCode","").toString();
    }
}
