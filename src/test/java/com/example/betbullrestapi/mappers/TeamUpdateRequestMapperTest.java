package com.example.betbullrestapi.mappers;

import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;
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
public class TeamUpdateRequestMapperTest {

    @InjectMocks
    private TeamUpdateRequestMapper updateRequestMapper;

    @Test
    public void toEntityTest(){
        //given
        final TeamUpdateRequest request = new TeamUpdateRequest("test name", LocalDate.of(1900,1,1), BigDecimal.valueOf(100_000));

        Team response = updateRequestMapper.toEntity(request);

        assertEquals(request.getBudget(), response.getBudget());
        assertEquals(request.getEstablishmentDate(), response.getEstablishmentDate());
        assertEquals(request.getTeamName(), response.getTeamName());
    }
}
