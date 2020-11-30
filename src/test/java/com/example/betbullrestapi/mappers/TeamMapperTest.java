package com.example.betbullrestapi.mappers;

import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.TeamDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class TeamMapperTest {

    @InjectMocks
    private TeamMapper teamMapper;

    @Test
    public void toDto(){
        //given
        Team team = new Team(1L, "team name", LocalDate.of(1900,1,1), BigDecimal.valueOf(100_000), null);

        TeamDto response = teamMapper.toDto(team);

        assertEquals(response.getBudget(), team.getBudget());
        assertEquals(response.getEstablishmentDate(), team.getEstablishmentDate());
        assertEquals(response.getTeamName(), team.getTeamName());
    }
}
