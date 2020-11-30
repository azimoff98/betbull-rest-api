package com.example.betbullrestapi.mappers;

import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.dto.PlayerDto;
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
public class PlayerMapperTest {

    @InjectMocks
    private PlayerMapper playerMapper;

    @Test
    public void toDtoTest(){
        //given
        final Player player = new Player(null, "Name", "Surname", "10", LocalDate.of(1980,10,10),
                LocalDate.of(2000,10,10), BigDecimal.valueOf(100_000), null);


        PlayerDto response = playerMapper.toDto(player);

        assertEquals(response.getBirthDate(), player.getBirthDate());
        assertEquals(response.getCareerStarted(), player.getCareerStarted());
        assertEquals(response.getNumberOnJersey(), player.getNumberOnJersey());
        assertEquals(response.getSurname(), player.getSurname());
        assertEquals(response.getName(), player.getName());
    }
}
