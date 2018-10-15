package com.peanutbutter.mail.kafka;

import com.peanutbutter.mail.entity.ReservedMail;
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
    private KafkaTemplate<String, ReservedMail> kafkaTemplate;

    @Override
    public void publish(ReservedMail reservedMail) {
        this.kafkaTemplate.send(TOPIC, reservedMail);
    }

    @KafkaListener(topics = TOPIC)
    public void subscribe(String message, Acknowledgment ack) {
        LOGGER.info(String.format("Message Received : %s", message));
        try {
            ReservedMail reservedMail = ReservedMail.deserializeJSON(message);

            if (reservedMail.getStatus() == Status.CONFIRMED){
                LOGGER.info(String.format("Send Confirmed Mail : %s", reservedMail.toString()));
                mailService.sendMail(reservedMail);
                ack.acknowledge();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
