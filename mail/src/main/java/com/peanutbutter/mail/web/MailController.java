package com.peanutbutter.mail.web;

import com.peanutbutter.mail.model.MailContent;
import com.peanutbutter.mail.service.MailService;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/mail")
@ToString
public class MailController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailContent.class);

    @Autowired
    private MailService mailService;

    @PostMapping
    public ResponseEntity<ResponseObj> trySendEmail(@RequestBody MailContent mailContent){
        return mailService.reserveMail(mailContent);
    }



    @PutMapping("/{id}")
    public ResponseEntity<Void> confirmSendEmail(@PathVariable Long id) {
        try {
            stockService.confirmStock(id);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
