package com.peanutbutter.mail.web;

import com.peanutbutter.mail.dto.TryRequest;
import com.peanutbutter.mail.dto.TryResponse;
import com.peanutbutter.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/mail")
public class MailController {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailController.class);

    @Autowired
    private MailService mailService;

//    @PostMapping
//    public ResponseEntity<TryResponse> trySendEmail(@RequestBody MailContent mailContent){
//        return mailService.reserveMail(mailContent);
//    }


    @PostMapping
    public ResponseEntity<TryResponse> trySendEmail(@RequestBody TryRequest tryRequest){
        return mailService.saveReservedResource(tryRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> confirmSendEmail(@PathVariable Long id) {
        try {
            mailService.confirmMail(id);
        } catch(IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
