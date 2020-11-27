package com.example.betbullrestapi.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String surname;
    private String numberOnJersey;
    private LocalDate careerStarted;
    private BigDecimal transferFee;
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;
}
