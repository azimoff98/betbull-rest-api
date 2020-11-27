package com.example.betbullrestapi.service.impl;

import com.example.betbullrestapi.domain.Team;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;
import com.example.betbullrestapi.exception.DomainNotFoundException;
import com.example.betbullrestapi.mapper.TeamCreationRequestMapper;
import com.example.betbullrestapi.mapper.TeamUpdateRequestMapper;
import com.example.betbullrestapi.repository.TeamRepository;
import com.example.betbullrestapi.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {


    private final TeamRepository teamRepository;
    private final TeamCreationRequestMapper teamCreationRequestMapper;
    private final TeamUpdateRequestMapper teamUpdateRequestMapper;

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
