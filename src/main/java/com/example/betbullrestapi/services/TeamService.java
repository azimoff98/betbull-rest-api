package com.example.betbullrestapi.services;


import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;

import java.util.List;

public interface TeamService {
    void create(TeamCreationRequest request);
    List<Team> findAll();
    List<Team> findWithPagination(int pageIndex, int pageSize);
    Team findById(Long id);
    Team findByPlayerId(Long playerId);
    List<Team> findByName(String name);
    void deleteById(long id);
    void update(TeamUpdateRequest request, Long id);
    void transfer(Long teamId, Long playerId);
}
