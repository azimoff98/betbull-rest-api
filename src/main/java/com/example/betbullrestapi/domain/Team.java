package com.example.betbullrestapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamName;
    private LocalDate establishmentDate;
    private BigDecimal budget;
    @OneToMany(mappedBy = "team", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Player> players;

    public Team(Long id, String teamName, LocalDate establishmentDate, BigDecimal budget) {
        this.id = id;
        this.teamName = teamName;
        this.establishmentDate = establishmentDate;
        this.budget = budget;
    }
}
