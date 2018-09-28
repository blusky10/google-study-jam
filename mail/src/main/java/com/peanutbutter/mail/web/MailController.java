package com.peanutbutter.mail.web;

import com.peanutbutter.mail.model.MailContent;
import com.peanutbutter.mail.model.ResponseObj;
import com.peanutbutter.mail.repository.MailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.Timestamp;
import java.util.Date;

@RestController
@RequestMapping("api/v1/mail")
public class MailController {

    @Autowired
    private MailRepository mailRepository;

    @PostMapping
    public ResponseEntity<ResponseObj> trySendEmail(@RequestBody MailContent mailContent){
        MailContent savedMailContent = mailRepository.save(mailContent);

        ResponseObj responseObj = buildResponseURI(savedMailContent.getId(), savedMailContent.getCreateTimeAt());

        return new ResponseEntity<>(responseObj, HttpStatus.CREATED);
    }

    private ResponseObj buildResponseURI(final Long id, final Timestamp created) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return new ResponseObj(location, created);
    }
}
