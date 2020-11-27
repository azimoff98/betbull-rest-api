package com.example.betbullrestapi.domain;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    private LocalDate establishmentDate;
    private BigDecimal budget;
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Player> players;

}
