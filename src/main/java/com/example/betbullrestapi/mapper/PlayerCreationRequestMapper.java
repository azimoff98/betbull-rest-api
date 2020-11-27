package com.example.betbullrestapi.mapper;

import com.example.betbullrestapi.domain.Player;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
import org.springframework.stereotype.Component;

@Component
public class PlayerCreationRequestMapper implements BaseMapper<PlayerCreationRequest, Player> {

    @Override
    public PlayerCreationRequest toDto(Player player) {
        return null;
    }

    @Override
    public Player toEntity(PlayerCreationRequest request) {
        Player player = new Player();
        player.setBirthDate(request.getBirthDate());
        player.setCareerStarted(request.getCareerStarted());
        player.setName(request.getName());
        player.setSurname(request.getSurname());
        player.setNumberOnJersey(request.getNumberOnJersey());
        return player;
    }
}
