package com.peanutbutter.mail.entity;

import com.peanutbutter.mail.dto.MailContent;
import com.peanutbutter.mail.enums.Status;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
public class ReservedMail {

    private static final long TIMEOUT = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "contents")
    private String contents;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "create_time_at")
    private LocalDateTime createTimeAt;

    @Column(name = "expires")
    private LocalDateTime expires;

    public ReservedMail() {
    }

    public ReservedMail(MailContent mailContent) {
        this.type = mailContent.getType();
        this.contents = mailContent.getContents();
        this.sender = mailContent.getSender();
        this.receiver = mailContent.getReceiver();
        this.createTimeAt = LocalDateTime.now();
        this.expires = createTimeAt.plus(TIMEOUT, ChronoUnit.SECONDS);
    }

    public void validate() {
        validateStatus();
        validateExpired();
    }

    public void validateStatus() {
        if(this.getStatus() == Status.CANCEL || this.getStatus() == Status.CONFIRMED) {
            throw new IllegalArgumentException("Invalidate Status");
        }
    }

    private void validateExpired() {
        if(LocalDateTime.now().isAfter(this.expires)) {
            throw new IllegalArgumentException("Expired");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public LocalDateTime getCreateTimeAt() {
        return createTimeAt;
    }

    public void setCreateTimeAt(LocalDateTime createTimeAt) {
        this.createTimeAt = createTimeAt;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public void setExpires(LocalDateTime expires) {
        this.expires = expires;
    }
}
