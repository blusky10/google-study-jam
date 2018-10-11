package com.peanutbutter.mail.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

/**
 * Created by Sanghyun KIM on 2018-10-11.
 */
@Service
public class KafkaServieImpl implements KafkaServie {

    private final static Logger LOGGER = LoggerFactory.getLogger(KafkaServieImpl.class);

    private final String TOPIC = "mail-service";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void publish(String message) {
        this.kafkaTemplate.send(TOPIC, message);
    }

    @KafkaListener(topics = TOPIC)
    public void subscribe(String message, Acknowledgment ack) {
        LOGGER.info(String.format("Message Received : %s", message));

//        try {
//            PaymentRequest paymentRequest = PaymentRequest.deserializeJSON(message);
//            paymentService.payOrder(paymentRequest.getOrderId(), paymentRequest.getPaymentAmt());
//            // Kafka Offset Manual Commit
//            ack.acknowledge();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
