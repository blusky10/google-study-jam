package com.peanutbutter.register.service;

import com.peanutbutter.register.model.ResponseObj;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class RestServiceImpl implements RestService{
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public ResponseObj doTry(String requestURL, Map<String, Object> requestBody) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<ResponseObj> response = restTemplate.postForEntity(requestURL, new HttpEntity(requestBody, headers), ResponseObj.class);

        if(response.getStatusCode() != HttpStatus.CREATED) {
            throw new RuntimeException(String.format("TRY Error[URI : %s][HTTP Status : %s]",
                    requestURL, response.getStatusCode().name()));
        }
        return response.getBody();
    }
}
