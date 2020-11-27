package com.example.betbullrestapi.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String teamName;
    private LocalDate establishmentDate;
    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<TeamPlayer> players;

}
