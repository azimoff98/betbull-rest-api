package com.example.betbullrestapi.dto;

import com.example.betbullrestapi.domain.Team;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PlayerDto {
    private Long id;
    private String name;
    private String surname;
    private String numberOnJersey;
    private LocalDate careerStarted;
    private LocalDate birthDate;
    private BigDecimal transferFee;
}
