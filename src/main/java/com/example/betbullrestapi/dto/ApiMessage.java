package com.example.betbullrestapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiMessage {
    private String message;
    private LocalDateTime date;
}
