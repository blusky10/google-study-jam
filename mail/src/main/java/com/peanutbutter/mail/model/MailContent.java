package com.peanutbutter.mail.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class MailContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "contents")
    private String contents;

    @Column(name = "sender")
    private String sender;

    @Column(name = "receivers")
    private List<String> receivers;

    @Column(name = "create_time_at")
    @CreationTimestamp
    private Timestamp createTimeAt;


}
