package com.peanutbutter.mail.service;

import com.peanutbutter.mail.dto.TryRequest;
import com.peanutbutter.mail.dto.TryResponse;
import com.peanutbutter.mail.entity.SendMail;
import com.peanutbutter.mail.entity.ReservedResource;
import com.peanutbutter.mail.enums.Status;
import com.peanutbutter.mail.kafka.KafkaServie;
import com.peanutbutter.mail.repository.MailRepository;
import com.peanutbutter.mail.repository.ReservedResourceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.Optional;


@Service
public class MailServiceImpl implements MailService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    @Autowired
    private MailRepository mailRepository;

    @Autowired
    private ReservedResourceRepository reservedResourceRepository;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private KafkaServie kafkaServie;


    @Override
    public ResponseEntity<TryResponse> saveReservedResource(TryRequest tryRequest) {
        ReservedResource reservedResource = new ReservedResource(tryRequest);
        reservedResourceRepository.save(reservedResource);
        TryResponse tryResponse = buildResponseURI(reservedResource.getId(), reservedResource.getCreatedTimeAt());
        return new ResponseEntity<>(tryResponse, HttpStatus.CREATED);
    }

//    @Override
//    public ResponseEntity<TryResponse> reserveMail(MailContent mailContent) {
//        LOGGER.debug("SendMail : " + mailContent.toString());
//
//        SendMail reservedMail = mailRepository.save(new SendMail(mailContent));
//
//        LOGGER.debug("responseObj : " + reservedMail.toString());
//
//        TryResponse responseObj = buildResponseURI(reservedMail.getId(), reservedMail.getCreateTimeAt());
//
//        return new ResponseEntity<>(responseObj, HttpStatus.CREATED);
//    }

    private TryResponse buildResponseURI(final Long id, final LocalDateTime created) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return new TryResponse(location, created);
    }

//    @Override
//    public void confirmMail(Long id) {
//
//        Optional<SendMail> optionalReservedMail = mailRepository.findById(id);
//
//        optionalReservedMail.orElseThrow(() -> new IllegalArgumentException("Not found"));
//
//        optionalReservedMail.ifPresent( reservedMail -> {
//            reservedMail.validate();
//
//            reservedMail.setStatus(Status.CONFIRMED);
//
//            LOGGER.info("Confirm Mail :" + id);
//
//            mailRepository.save(reservedMail);
//
//            LOGGER.info("Publish to Kafka Confirmed Mail :" + reservedMail.toString());
//            kafkaServie.publish(reservedMail);
//
//        });
//    }


    @Override
    public void confirmMail(Long id) {

        Optional<ReservedResource> reservedResource = reservedResourceRepository.findById(id);

        reservedResource.orElseThrow(() -> new IllegalArgumentException("Not found"));

        reservedResource.ifPresent( resource -> {
            resource.validate();
            resource.setStatus(Status.CONFIRMED);

            LOGGER.info("Confirm Mail :" + id);

            reservedResourceRepository.save(resource);

            LOGGER.info("Publish to Kafka Confirmed Mail :" + resource.toString());

            kafkaServie.publish(resource.getResources());
        });
    }

    @Override
    public void sendMail(SendMail sendMail){

        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(sendMail.getReceiver());
        registrationEmail.setSubject(sendMail.getSubject());
        registrationEmail.setText(sendMail.getContents());
        registrationEmail.setFrom("noreply@domain.com");

        LOGGER.info("Send Mail " + registrationEmail.toString());

        mailSender.send(registrationEmail);

        mailRepository.save(sendMail);
    }

}
