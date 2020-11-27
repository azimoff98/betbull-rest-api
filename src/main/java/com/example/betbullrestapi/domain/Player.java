package com.example.betbullrestapi.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private String numberOnJersey;
    private LocalDate careerStarted;
    private LocalDate birthDate;
    private BigDecimal transferFee;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
