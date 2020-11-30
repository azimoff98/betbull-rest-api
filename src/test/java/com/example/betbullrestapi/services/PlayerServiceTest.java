package com.example.betbullrestapi.services;

import com.example.betbullrestapi.BetbullRestApiApplication;
import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
import com.example.betbullrestapi.dto.vm.PlayerUpdateRequest;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class PlayerServiceTest {

    @InjectMocks
    private PlayerServiceImpl playerService;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private FeeDefinerServiceImpl feeDefinerService;
    @Mock
    private PlayerCreationRequestMapper playerCreationRequestMapper;


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

        playerService.create(request);

        verify(playerRepository, times(1)).save(player);



    }

    @Test
    public void findAllTest(){
        //expected
        List<Player> players = new ArrayList<>();
        players.add(
                new Player(1L, "Name", "Surname", "10", LocalDate.of(2020,11,20),
                        LocalDate.of(2000,10,10), BigDecimal.valueOf(100_000), null)
        );
        when(playerRepository.findAll()).thenReturn(players);

        List<Player> response = playerService.findAll();
        assertEquals(response.size(), players.size());
        assertEquals(response.get(0).getBirthDate(), players.get(0).getBirthDate());
        assertEquals(response.get(0).getCareerStarted(), players.get(0).getCareerStarted());
        assertEquals(response.get(0).getName(), players.get(0).getName());
        assertEquals(response.get(0).getSurname(), players.get(0).getSurname());
        assertEquals(response.get(0).getNumberOnJersey(), players.get(0).getNumberOnJersey());
    }

    @Test
    public void findByIdTest(){
        //given
        final long id = 5;
        //expected
        Player player = new Player(1L, "Name", "Surname", "10", LocalDate.of(2020,11,20),
                LocalDate.of(2000,10,10), BigDecimal.valueOf(100_000), null);

        when(playerRepository.findById(id)).thenReturn(Optional.of(player));

        Player response = playerService.findById(id);

        assertEquals(player.getNumberOnJersey(), response.getNumberOnJersey());
        assertEquals(player.getName(), response.getName());
        assertEquals(player.getSurname(), response.getSurname());
        assertEquals(player.getBirthDate(), response.getBirthDate());
        assertEquals(player.getCareerStarted(), response.getCareerStarted());
        assertEquals(player.getTransferFee(), response.getTransferFee());
    }

    @Test
    public void findByNameTest(){
        //given
        final String name = "Test";

        Player player = new Player(1L, "Test Name", "Surname", "10", LocalDate.of(2020,11,20),
                LocalDate.of(2000,10,10), BigDecimal.valueOf(100_000), null);

        when(playerRepository.findByNames(name)).thenReturn(Arrays.asList(player));

        List<Player> response = playerService.findByName(name);

        assertEquals(response.get(0).getTransferFee(), player.getTransferFee());
        assertEquals(response.get(0).getBirthDate(), player.getBirthDate());
        assertEquals(response.get(0).getCareerStarted(), player.getCareerStarted());
    }

    @Test
    public void deleteByIdTest(){
        //given
        final long id = 10;

        doNothing().when(playerRepository).deleteById(id);

        playerService.deleteById(id);
        verify(playerRepository, times(1)).deleteById(id);
    }





}
