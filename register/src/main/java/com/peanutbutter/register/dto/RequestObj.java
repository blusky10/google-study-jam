package com.peanutbutter.register.dto;

import java.util.HashMap;
import java.util.Map;

public class RequestObj {
    private String url;
    private Map<String, Object> body = new HashMap<>();

    public RequestObj() {
    }

    public RequestObj(String url, Map<String, Object> requestBody) {
        this.url = url;
        this.body = requestBody;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Map<String, Object> getBody() {
        return body;
    }

    public void setBody(Map<String, Object> body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "RequestObj{" +
                "url='" + url + '\'' +
                ", body=" + body +
                '}';
    }
}
