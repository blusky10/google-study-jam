package com.peanutbutter.register.service;

import com.peanutbutter.register.dto.RequestObj;
import com.peanutbutter.register.dto.ResponseObj;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RestServiceImpl implements RestService{

    private static final Logger LOGGER = LoggerFactory.getLogger(RestServiceImpl.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private RetryTemplate retryTemplate;

    @Override
    public List<ResponseObj> doTry(List<RequestObj> requestObjList) {
        List<ResponseObj> requestObjs = new ArrayList<>();

        requestObjList.forEach(requestObj -> {
                try {
                    ResponseObj responseObj = retryTemplate.execute((RetryCallback<ResponseObj, RestClientException>) context -> {
                        HttpHeaders headers = new HttpHeaders();
                        headers.setContentType(MediaType.APPLICATION_JSON);

                        LOGGER.info("[Register-Service] requestObj URI : " + requestObj.toString());

                        ResponseEntity<ResponseObj> response = restTemplate.postForEntity(requestObj.getUrl(),
                                new HttpEntity(requestObj, headers), ResponseObj.class);

                        LOGGER.info(String.format("[Register-Service] ResponseObj URI :%s", response.getBody().getUri()));

                        return response.getBody();
                    });

                    requestObjs.add(responseObj);
                } catch (RestClientException e) {
//                    cancelAll(participantLinks);
                    throw new RuntimeException(String.format("Try Error[URI : %s]", requestObj.getUrl()), e);
                }
            }
        );

        return requestObjs;
    }

    @Override
    public void confirmAll(List<ResponseObj> responseObjList) {
        responseObjList.forEach(responseObj -> {
            try {
                retryTemplate.execute((RetryCallback<Void, RestClientException>) context -> {
                    restTemplate.put(responseObj.getUri(), null);
                    return null;
                });

            } catch (RestClientException e) {
                LOGGER.error(String.format("Confirm Error[URI : %s]", responseObj.getUri().toString()), e);
            }
        });
    }
}
