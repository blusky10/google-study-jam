package com.peanutbutter.mail.dto;

import lombok.*;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResponseObj implements Serializable{
    private URI uri;
    private LocalDateTime expires;
}

