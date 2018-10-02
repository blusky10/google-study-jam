package com.peanutbutter.register.dto;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class RequestObj implements Serializable {
    private String url;
    private Map<String, Object> requestBody = new HashMap<>();

    public RequestObj(String url, Map<String, Object> requestBody) {
        this.url = url;
        this.requestBody = requestBody;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(Map<String, Object> requestBody) {
        this.requestBody = requestBody;
    }
}
