package com.peanutbutter.mail.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.peanutbutter.mail.dto.TryRequest;
import com.peanutbutter.mail.enums.Status;

import javax.persistence.*;
import javax.xml.ws.Service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
public class ReservedResource {

    private static final long TIMEOUT = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String resources;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdTimeAt;

    private LocalDateTime expires;

    public ReservedResource() {
    }

    public ReservedResource(TryRequest tryRequest) {
        try {
            this.resources = tryRequest.serializeJSON();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        this.status = Status.TRY;
        this.createdTimeAt = LocalDateTime.now();
        this.expires = createdTimeAt.plus(TIMEOUT, ChronoUnit.MINUTES);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResources() {
        return resources;
    }

    public void setResources(String resources) {
        this.resources = resources;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedTimeAt() {
        return createdTimeAt;
    }

    public void setCreatedTimeAt(LocalDateTime createdTimeAt) {
        this.createdTimeAt = createdTimeAt;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }

    public void validate() {
        validateStatus();
        validateExpired();
    }

    private void validateStatus() {
        if(this.getStatus() != Status.TRY ) {
            throw new IllegalArgumentException("Invalidate Status");
        }
    }

    private void validateExpired() {
        if(LocalDateTime.now().isAfter(this.expires)) {
            throw new IllegalArgumentException("Expired");
        }
    }

    public static SendMail deserializeJSON(final String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, SendMail.class);
    }

    @Override
    public String toString() {
        return "ReservedResource{" +
                "id=" + id +
                ", resources='" + resources + '\'' +
                ", status=" + status +
                ", createdTimeAt=" + createdTimeAt +
                ", expires=" + expires +
                '}';
    }
}
