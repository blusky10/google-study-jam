package com.peanutbutter.register.service;

import com.peanutbutter.register.model.ResponseObj;

import java.net.URI;
import java.util.Map;

public interface RestService {

    ResponseObj doTry(final String requestURL, final Map<String, Object> requestBody);

    public void confirmAll(final URI... uris);
}
