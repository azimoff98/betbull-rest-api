package com.example.betbullrestapi.dto;

import com.example.betbullrestapi.domains.Player;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamDto {

    private Long id;
    private String teamName;
    private LocalDate establishmentDate;
    private BigDecimal budget;
    private List<Player> players;
}
