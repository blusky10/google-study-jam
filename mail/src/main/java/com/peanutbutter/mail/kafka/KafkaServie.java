package com.peanutbutter.mail.kafka;

import com.peanutbutter.mail.entity.ReservedMail;

public interface KafkaServie {

    void publish(ReservedMail reservedMail);
}
