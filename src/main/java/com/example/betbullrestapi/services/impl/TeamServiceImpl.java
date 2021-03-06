package com.example.betbullrestapi.services.impl;

import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;
import com.example.betbullrestapi.exceptions.DomainNotFoundException;
import com.example.betbullrestapi.exceptions.TransferException;
import com.example.betbullrestapi.mappers.TeamCreationRequestMapper;
import com.example.betbullrestapi.repository.PlayerRepository;
import com.example.betbullrestapi.repository.TeamRepository;
import com.example.betbullrestapi.services.FeeDefinerService;
import com.example.betbullrestapi.services.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {


    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final FeeDefinerService feeDefinerService;
    private final TeamCreationRequestMapper teamCreationRequestMapper;


    @Override
    public void create(TeamCreationRequest request) {
        log.info("Team creation process started...");
        Team team = teamCreationRequestMapper.toEntity(request);
        teamRepository.save(team);
    }

    @Override
    public List<Team> findAll() {
        return (List<Team>) teamRepository.findAll();
    }

    @Override
    public List<Team> findWithPagination(int pageIndex, int pageSize) {
        return teamRepository.findAll(PageRequest.of(pageIndex, pageSize)).toList();

    }

    @Override
    public Team findById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException("Team not found with id: " + id));
    }

    @Override
    public Team findByPlayerId(Long playerId) {
        return teamRepository.findByPlayerId(playerId);
    }

    @Override
    public List<Team> findByName(String name) {
        return teamRepository.findByNames(name);
    }

    @Override
    public void deleteById(long id) {
        teamRepository.deleteById(id);
    }

    @Override
    public void update(TeamUpdateRequest request, Long id) {
        log.info("Team updating process started...");
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException("Team not found with id: " + id));

        teamRepository.save(teamUpdater(team, request));
    }

    @Override
    public void transfer(Long teamId, Long playerId) {
        log.info("Transfer process starting...");
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new DomainNotFoundException("No team found with id: " + teamId));
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new DomainNotFoundException("No player found with id: " + playerId));
        if(team.getPlayers().contains(player)){
            throw new TransferException("Player with id: " + playerId + " is already team member");
        }
        BigDecimal transferFeeWithCommission = feeDefinerService.definePlayerFeeWithCommission(player);
        log.info("Transfer fee for player with id: {} is : {}", playerId, transferFeeWithCommission);

        if((team.getBudget().compareTo(transferFeeWithCommission)) < 0){
            throw new TransferException("Team with id: " + teamId + " cannot transfer player with id: " + playerId + ". Budget is not enough for transfer");
        }
        log.info("Transfer completing process...");
        player.setTeam(team);
        playerRepository.save(player);
        team.setBudget(team.getBudget().subtract(transferFeeWithCommission));
        teamRepository.save(team);
        log.info("Transfer completed successfully..");

    }

    private Team teamUpdater(Team team, TeamUpdateRequest request){
        if(request.getBudget() != null)
            team.setBudget(request.getBudget());
        if(request.getEstablishmentDate() != null)
            team.setEstablishmentDate(request.getEstablishmentDate());
        if(request.getTeamName() != null)
            team.setTeamName(request.getTeamName());

        return team;
    }
}
