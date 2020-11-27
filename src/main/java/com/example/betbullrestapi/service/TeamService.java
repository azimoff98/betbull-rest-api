package com.example.betbullrestapi.service;


import com.example.betbullrestapi.domain.Team;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface TeamService {
    void create(TeamCreationRequest request);
    List<Team> findAll();
    List<Team> findWithPagination(int pageIndex, int pageSize);
    Team findById(Long id);
    List<Team> findByName(String name);
    void deleteById(long id);
    void update(TeamUpdateRequest request, Long id);
}
