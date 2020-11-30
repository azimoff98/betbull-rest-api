package com.example.betbullrestapi.services;

import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;
import com.example.betbullrestapi.mappers.TeamCreationRequestMapper;
import com.example.betbullrestapi.repository.PlayerRepository;
import com.example.betbullrestapi.repository.TeamRepository;
import com.example.betbullrestapi.services.impl.TeamServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
public class TeamServiceTest {

    @InjectMocks
    private TeamServiceImpl teamService;
    @Mock
    private TeamRepository teamRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Mock
    private FeeDefinerService feeDefinerService;
    @Mock
    private TeamCreationRequestMapper teamCreationRequestMapper;



    @Test
    public void createTest(){
        //given
        final TeamCreationRequest request = new TeamCreationRequest();
        request.setBudget(BigDecimal.valueOf(100_000_000));
        request.setEstablishmentDate(LocalDate.of(1900,1,1));
        request.setTeamName("Test team name");

        Team team = new Team();
        team.setBudget(BigDecimal.valueOf(100_000_000));
        team.setEstablishmentDate(LocalDate.of(1900,1,1));
        team.setTeamName("Test team name");

        when(teamCreationRequestMapper.toEntity(request)).thenReturn(team);

        teamService.create(request);

        verify(teamCreationRequestMapper, times(1)).toEntity(request);
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    public void findAllTest(){
        //expected
        List<Team> teams = new ArrayList<Team>(){{
            add(new Team(1L, "Test team", LocalDate.of(2000,1,1), BigDecimal.valueOf(100_000),null));
            add(new Team(2L, "Test team 2", LocalDate.of(2000,1,1), BigDecimal.valueOf(100_000),null));
        }};

        when(teamRepository.findAll()).thenReturn(teams);

        List<Team> response = teamService.findAll();

        assertEquals(response.size(), teams.size());
        assertEquals(response.get(0).getBudget(), teams.get(0).getBudget());
        assertEquals(response.get(0).getTeamName(), teams.get(0).getTeamName());
        assertEquals(response.get(0).getEstablishmentDate(), teams.get(0).getEstablishmentDate());
    }

    @Test
    public void findByIdTest(){
        //given
        final long teamId = 15;
        //expected
        Team team = new Team();
        team.setBudget(BigDecimal.valueOf(100_000_000));
        team.setEstablishmentDate(LocalDate.of(1900,1,1));
        team.setTeamName("Test team name");

        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));

        Team response = teamService.findById(teamId);

        assertEquals(response.getEstablishmentDate(), team.getEstablishmentDate());
        assertEquals(response.getBudget(), team.getBudget());
        assertEquals(response.getTeamName(), team.getTeamName());

    }

    @Test
    public void findByPlayerIdTest(){
        //given
        final long playerId = 15;
        //expected
        Team team = new Team(1L, "Test Team", LocalDate.of(2000,10,20), BigDecimal.valueOf(100_000), null);

        when(teamRepository.findByPlayerId(playerId)).thenReturn(team);

        Team response = teamService.findByPlayerId(playerId);

        assertEquals(response.getTeamName(), team.getTeamName());
        assertEquals(response.getBudget(), team.getBudget());
        assertEquals(response.getEstablishmentDate(), team.getEstablishmentDate());
    }

    @Test
    public void findByNameTest(){
        //given
        final String name = "test";

        //expected
        final Team team = new Team(1L, "Test team", LocalDate.of(2000,10,1), BigDecimal.valueOf(100_000), null);

        when(teamRepository.findByNames(name)).thenReturn(Collections.singletonList(team));

        List<Team> response = teamService.findByName(name);

        assertEquals(response.get(0).getEstablishmentDate(), team.getEstablishmentDate());
        assertEquals(response.get(0).getTeamName(), team.getTeamName());
        assertEquals(response.get(0).getBudget(), team.getBudget());

    }

    @Test
    public void deleteByIdTest(){
        //given
        final long teamId = 10L;

        teamService.deleteById(teamId);

        verify(teamRepository, times(1)).deleteById(teamId);
    }

    @Test
    public void updateTest(){
        //given
        final TeamUpdateRequest request = new TeamUpdateRequest("Test team", LocalDate.of(2000,10,10), BigDecimal.valueOf(100_000));
        final long id = 5L;
        //expected
        final Team team = new Team(5L, "Team name", LocalDate.of(2000,10,10), BigDecimal.valueOf(100_000), null);
        when(teamRepository.findById(id)).thenReturn(Optional.of(team));

        teamService.update(request, id);

        verify(teamRepository, times(1)).findById(id);
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    public void transferTest(){
        //given
        final long playerId = 10;
        final long teamId = 1;

        //expected
        Team team = new Team(1L, "Team name", LocalDate.of(2000,1,1), BigDecimal.valueOf(100_000_000), new ArrayList<>());
        Player player = new Player(10L, "Name", "Surname", "10", LocalDate.of(2015,1,1),
                LocalDate.of(1998,1,1), BigDecimal.valueOf(100_000), null);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(player));
        when(teamRepository.findById(teamId)).thenReturn(Optional.of(team));
        when(feeDefinerService.definePlayerFeeWithCommission(player)).thenReturn(BigDecimal.valueOf(110_000));

        teamService.transfer(teamId, playerId);

        verify(teamRepository, times(1)).findById(teamId);
        verify(playerRepository, times(1)).findById(playerId);
    }
}
