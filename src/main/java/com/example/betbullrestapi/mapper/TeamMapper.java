package com.example.betbullrestapi.mapper;

import com.example.betbullrestapi.domain.Team;
import com.example.betbullrestapi.dto.TeamDto;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper implements BaseMapper<TeamDto, Team> {

    @Override
    public TeamDto toDto(Team team) {
        TeamDto teamDto = new TeamDto();
        teamDto.setBudget(team.getBudget());
        teamDto.setEstablishmentDate(team.getEstablishmentDate());
        teamDto.setId(team.getId());
        teamDto.setPlayers(team.getPlayers());
        teamDto.setTeamName(team.getTeamName());
        return teamDto;
    }

    @Override
    public Team toEntity(TeamDto teamDto) {
        return null;
    }
}
