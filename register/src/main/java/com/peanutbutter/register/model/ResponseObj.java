package com.peanutbutter.register.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.net.URI;
import java.sql.Timestamp;

@Getter
@Setter
public class ResponseObj implements Serializable {

    private URI uri;
    private Timestamp expires;
}
