package com.peanutbutter.mail.entity;

import com.peanutbutter.mail.dto.MailContent;
import com.peanutbutter.mail.enums.Status;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@ToString
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

    @Column(name = "receivers")
    private List<String> receivers;

    @Column(name = "create_time_at")
    private LocalDateTime createTimeAt;

    @Column(name = "expires")
    private LocalDateTime expires;

    public ReservedMail(MailContent mailContent) {
        this.type = mailContent.getType();
        this.contents = mailContent.getContents();
        this.sender = mailContent.getSender();
        this.receivers = mailContent.getReceivers();
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

}
