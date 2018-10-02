package com.peanutbutter.register.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;

@Getter
@Setter
public class ResponseObj implements Serializable {

    private URI uri;
    private LocalDateTime expires;
}
