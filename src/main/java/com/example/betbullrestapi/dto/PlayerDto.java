package com.example.betbullrestapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
    private Long id;
    private String name;
    private String surname;
    private String numberOnJersey;
    private LocalDate careerStarted;
    private LocalDate birthDate;
    private BigDecimal transferFee;
}
