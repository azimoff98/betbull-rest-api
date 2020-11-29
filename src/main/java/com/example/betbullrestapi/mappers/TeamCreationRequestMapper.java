package com.example.betbullrestapi.mappers;

import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component("teamCreationRequestMapper")
public class TeamCreationRequestMapper implements BaseMapper<TeamCreationRequest, Team> {

    @Override
    public TeamCreationRequest toDto(Team team) {

        return null;
    }

    @Override
    public Team toEntity(TeamCreationRequest request) {
        Team team = new Team();
        team.setEstablishmentDate(request.getEstablishmentDate());
        team.setTeamName(request.getTeamName());
        team.setBudget(request.getBudget());
        team.setPlayers(Collections.EMPTY_LIST);
        return team;
    }
}
