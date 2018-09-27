package com.peanutbutter.mail.web;

import com.peanutbutter.mail.model.MailContent;
import com.peanutbutter.mail.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MailController {

    @Autowired
    private MailRepository mailRepository;

    @PostMapping
    public Long trySendEmail(){
        MailContent mailContent = new MailContent();


        MailContent savedMailContent = mailRepository.save(mailContent);

        return
    }
}
