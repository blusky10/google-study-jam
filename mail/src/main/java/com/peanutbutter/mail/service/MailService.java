package com.peanutbutter.mail.service;


import com.peanutbutter.mail.dto.MailContent;
import com.peanutbutter.mail.dto.ResponseObj;
import com.peanutbutter.mail.entity.ReservedMail;
import org.springframework.http.ResponseEntity;

public interface MailService {

    ResponseEntity<ResponseObj> reserveMail(MailContent mailContent);

    void confirmMail(Long id);

    void sendMail(ReservedMail reservedMail);
}
