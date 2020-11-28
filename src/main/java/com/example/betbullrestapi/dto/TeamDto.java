package com.example.betbullrestapi.dto;

import com.example.betbullrestapi.domain.Player;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class TeamDto {

    private Long id;
    private String teamName;
    private LocalDate establishmentDate;
    private BigDecimal budget;
    private List<Player> players;
}
