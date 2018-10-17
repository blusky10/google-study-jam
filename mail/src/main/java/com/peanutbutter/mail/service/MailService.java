package com.peanutbutter.mail.service;


import com.peanutbutter.mail.dto.TryRequest;
import com.peanutbutter.mail.dto.TryResponse;
import com.peanutbutter.mail.entity.SendMail;
import org.springframework.http.ResponseEntity;

public interface MailService {

//    ResponseEntity<TryResponse> reserveMail(MailContent mailContent);

    void confirmMail(Long id);

    void sendMail(SendMail reservedMail);

    ResponseEntity<TryResponse> saveReservedResource(TryRequest request);
}
