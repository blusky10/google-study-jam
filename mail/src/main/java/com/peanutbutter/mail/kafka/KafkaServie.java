package com.peanutbutter.mail.kafka;

import com.peanutbutter.mail.entity.ReservedResource;

public interface KafkaServie {

//    void publish(SendMail reservedMail);

    void publish(String message);
}
