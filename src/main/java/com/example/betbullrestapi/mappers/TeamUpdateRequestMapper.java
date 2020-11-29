package com.example.betbullrestapi.mappers;

import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class TeamUpdateRequestMapper implements BaseMapper<TeamUpdateRequest, Team> {

    @Override
    public TeamUpdateRequest toDto(Team team) {
        return null;
    }

    @Override
    public Team toEntity(TeamUpdateRequest request) {
        Team team = new Team();
        team.setBudget(request.getBudget());
        team.setTeamName(request.getTeamName());
        team.setEstablishmentDate(request.getEstablishmentDate());
        team.setPlayers(Collections.EMPTY_LIST);

        return null;
    }
}
