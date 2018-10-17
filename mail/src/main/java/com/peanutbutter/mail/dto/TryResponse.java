package com.peanutbutter.mail.dto;

import java.io.Serializable;
import java.net.URI;
import java.time.LocalDateTime;

public class TryResponse implements Serializable{
    private URI uri;
    private LocalDateTime expires;

    public TryResponse(URI uri, LocalDateTime expires) {
        this.uri = uri;
        this.expires = expires;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }
}

