package com.example.betbullrestapi.mappers;

import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.dto.PlayerDto;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
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
public class PlayerCreationRequestMapperTest {

    @InjectMocks
    private PlayerCreationRequestMapper playerCreationRequestMapper;

    @Test
    public void toEntityTest(){
        //given
        final PlayerCreationRequest request = new PlayerCreationRequest("Name", "Surname", "10",
                LocalDate.of(2000,10,10), LocalDate.of(1980,10,10), null);


        Player response = playerCreationRequestMapper.toEntity(request);

        assertEquals(response.getCareerStarted(), request.getCareerStarted());
        assertEquals(response.getName(), request.getName());
        assertEquals(response.getSurname(), request.getSurname());
        assertEquals(response.getNumberOnJersey(), request.getNumberOnJersey());
        assertEquals(response.getBirthDate(), request.getBirthDate());
    }



}
