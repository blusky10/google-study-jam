package com.peanutbutter.mail.dto;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class MailContent {

    private String type;
    private String contents;
    private String sender;
    private List<String> receivers;

}
