package com.tijapay.posgateway.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvertToJson {

    public static String setJsonString(Object content) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString;

        try {

            jsonInString = mapper.writeValueAsString(content);

            return jsonInString;
        } catch (JsonProcessingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
