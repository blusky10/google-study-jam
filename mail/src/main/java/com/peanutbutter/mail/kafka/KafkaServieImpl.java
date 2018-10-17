package com.peanutbutter.mail.kafka;

import com.peanutbutter.mail.entity.SendMail;
import com.peanutbutter.mail.entity.ReservedResource;
import com.peanutbutter.mail.enums.Status;
import com.peanutbutter.mail.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class KafkaServieImpl implements KafkaServie {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaServieImpl.class);

    @Autowired
    private MailService mailService;

    private final String TOPIC = "mail-service";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

//    @Override
//    public void publish(SendMail reservedMail) {
//        this.kafkaTemplate.send(TOPIC, reservedMail);
//    }

    @Override
    public void publish(String message) {
        this.kafkaTemplate.send(TOPIC, message);
    }

    @KafkaListener(topics = TOPIC)
    public void subscribe(String message, Acknowledgment ack) {
        LOGGER.info("[Mail-Service] Message Received : " + message);
        try {

            SendMail sendMail = ReservedResource.deserializeJSON(message);

            LOGGER.info("[Mail-Service] Send Confirmed Mail : " + sendMail.toString());
            mailService.sendMail(sendMail);
            ack.acknowledge();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
