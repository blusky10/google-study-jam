package com.peanutbutter.register.service;

import com.peanutbutter.register.dto.RequestObj;
import com.peanutbutter.register.dto.ResponseObj;

import java.util.List;

public interface RestService {

    List<ResponseObj> doTry(List<RequestObj> requestObjList);

    void confirmAll(List<ResponseObj> responseObjList);
}
