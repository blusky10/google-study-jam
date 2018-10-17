package com.peanutbutter.mail.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class TryRequest {

    private Map<String, String> body = new HashMap<>();

    public Map<String, String> getBody() {
        return body;
    }

    public void setBody(Map<String, String> body) {
        this.body = body;
    }

    public String serializeJSON() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

    @Override
    public String toString() {
        return "TryRequest{" +
                "body=" + body +
                '}';
    }
}
