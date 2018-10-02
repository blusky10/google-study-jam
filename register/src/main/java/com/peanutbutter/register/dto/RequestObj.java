package com.peanutbutter.register.dto;

import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class RequestObj implements Serializable {
    private String url;
    private Map<String, Object> requestBody = new HashMap<>();
}
