package com.example.betbullrestapi.services;

import com.example.betbullrestapi.BetbullRestApiApplication;
import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
import com.example.betbullrestapi.mappers.PlayerCreationRequestMapper;
import com.example.betbullrestapi.repository.PlayerRepository;
import com.example.betbullrestapi.repository.TeamRepository;
import com.example.betbullrestapi.services.impl.FeeDefinerServiceImpl;
import com.example.betbullrestapi.services.impl.PlayerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerServiceImpl playerService;
    @MockBean
    private PlayerRepository playerRepository;
    @MockBean
    private TeamRepository teamRepository;
    @MockBean
    private FeeDefinerServiceImpl feeDefinerService;
    @MockBean
    private PlayerCreationRequestMapper playerCreationRequestMapper;

    @Test
    public void findAllTest(){
        //expected
        List<Player> players = new ArrayList<>();
        players.add(
                new Player(1L, "Name", "Surname", "10", LocalDate.of(2020,11,20),
                        LocalDate.of(2000,10,10), BigDecimal.valueOf(100_000), null)
        );
        given(playerRepository.findAll()).willReturn(players);
        List<Player> response = playerService.findAll();
        assertEquals(response.get(0).getBirthDate(), players.get(0).getBirthDate());
    }

    @Test
    public void createTest(){
        //given
        final PlayerCreationRequest request = new PlayerCreationRequest("Test Name", "Test Surname", "10",
                LocalDate.of(2002,10,10), LocalDate.of(2020,10,10), 5L);
        final Team team = new Team(1L, "Test Team name", LocalDate.of(1900,1,1), BigDecimal.valueOf(1_000_000));
        final Player player = new Player();
        player.setTeam(team);
        player.setTransferFee(BigDecimal.valueOf(100_000));
        player.setBirthDate(request.getBirthDate());
        player.setCareerStarted(request.getCareerStarted());
        player.setNumberOnJersey(request.getNumberOnJersey());
        player.setName(request.getName());
        player.setSurname(request.getSurname());

        given(feeDefinerService.definePlayerFee(request.getBirthDate(), request.getCareerStarted())).willReturn(BigDecimal.valueOf(100_000));
        when(teamRepository.findById(request.getTeamId())).thenReturn(Optional.of(team));
        when(playerCreationRequestMapper.toEntity(request)).thenReturn(player);
//        doNothing().when(playerRepository).save(player);




    }




}
