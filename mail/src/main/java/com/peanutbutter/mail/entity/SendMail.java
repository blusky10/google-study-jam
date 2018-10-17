package com.peanutbutter.mail.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SendMail {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String type;

    private String contents;

    private String sender;

    private String receiver;

    private String subject;

    private LocalDateTime createTimeAt;

    public SendMail() {
        this.createTimeAt = LocalDateTime.now();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public LocalDateTime getCreateTimeAt() {
        return createTimeAt;
    }

    public void setCreateTimeAt(LocalDateTime createTimeAt) {
        this.createTimeAt = createTimeAt;
    }

    @Override
    public String toString() {
        return "SendMail{" +
                "id=" + id +
                ", type='" + type + '\'' +
                ", contents='" + contents + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", subject='" + subject + '\'' +
                ", createTimeAt=" + createTimeAt +
                '}';
    }
}
