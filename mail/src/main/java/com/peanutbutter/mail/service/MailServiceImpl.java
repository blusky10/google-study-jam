package com.peanutbutter.mail.service;

import com.peanutbutter.mail.dto.MailContent;
import com.peanutbutter.mail.dto.ResponseObj;
import com.peanutbutter.mail.entity.ReservedMail;
import com.peanutbutter.mail.enums.Status;
import com.peanutbutter.mail.kafka.KafkaServie;
import com.peanutbutter.mail.repository.MailRepository;
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
    private JavaMailSender mailSender;

    @Autowired
    private KafkaServie kafkaServie;

    @Override
    public ResponseEntity<ResponseObj> reserveMail(MailContent mailContent) {
        LOGGER.debug("ReservedMail : " + mailContent.toString());

        ReservedMail reservedMail = mailRepository.save(new ReservedMail(mailContent));

        LOGGER.debug("responseObj : " + reservedMail.toString());

        ResponseObj responseObj = buildResponseURI(reservedMail.getId(), reservedMail.getCreateTimeAt());

        return new ResponseEntity<>(responseObj, HttpStatus.CREATED);
    }

    private ResponseObj buildResponseURI(final Long id, final LocalDateTime created) {
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
        return new ResponseObj(location, created);
    }

    @Override
    public void confirmMail(Long id) {

        Optional<ReservedMail> optionalReservedMail = mailRepository.findById(id);

        optionalReservedMail.orElseThrow(() -> new IllegalArgumentException("Not found"));

        optionalReservedMail.ifPresent( reservedMail -> {
            reservedMail.validate();

            reservedMail.setStatus(Status.CONFIRMED);

            LOGGER.info("Confirm Mail :" + id);

            mailRepository.save(reservedMail);

            LOGGER.info("Publish to Kafka Confirmed Mail :" + reservedMail.toString());
            kafkaServie.publish(reservedMail);

        });
    }

    @Override
    public void sendMail(ReservedMail reservedMail){

        SimpleMailMessage registrationEmail = new SimpleMailMessage();
        registrationEmail.setTo(reservedMail.getReceiver());
        registrationEmail.setSubject(reservedMail.getSubject());
        registrationEmail.setText(reservedMail.getContents());
        registrationEmail.setFrom("noreply@domain.com");

        LOGGER.info("Send Mail " + registrationEmail.toString());

        mailSender.send(registrationEmail);
    }
}
