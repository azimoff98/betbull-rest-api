package com.example.betbullrestapi.mappers;

import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
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
public class TeamCreationRequestMapperTest {

    @InjectMocks
    private TeamCreationRequestMapper creationRequestMapper;

    @Test
    public void toEntityTest(){
        //given
        final TeamCreationRequest creationRequest =
                new TeamCreationRequest("test", LocalDate.of(1900,1,1), BigDecimal.valueOf(100_000));

        Team response = creationRequestMapper.toEntity(creationRequest);

        assertEquals(creationRequest.getBudget(), response.getBudget());
        assertEquals(creationRequest.getTeamName(), response.getTeamName());
        assertEquals(creationRequest.getEstablishmentDate(), response.getEstablishmentDate());

    }
}
