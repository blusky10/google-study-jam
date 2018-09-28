package com.peanutbutter.register.model;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.sql.Timestamp;

@Getter
@Setter
public class ResponseObj {

    private URI uri;
    private Timestamp expires;
}
