package com.peanutbutter.mail.kafka;

import com.peanutbutter.mail.entity.ReservedMail;

/**
 * Created by Sanghyun KIM on 2018-10-11.
 */
public interface KafkaServie {
    void publish(String message);
}
