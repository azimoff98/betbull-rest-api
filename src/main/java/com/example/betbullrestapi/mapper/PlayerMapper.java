package com.example.betbullrestapi.mapper;

import com.example.betbullrestapi.domain.Player;
import com.example.betbullrestapi.dto.PlayerDto;
import org.springframework.stereotype.Component;

@Component
public class PlayerMapper implements BaseMapper<PlayerDto, Player> {


    @Override
    public PlayerDto toDto(Player player) {
        PlayerDto playerDto = new PlayerDto();
        playerDto.setId(player.getId());
        playerDto.setBirthDate(player.getBirthDate());
        playerDto.setTransferFee(player.getTransferFee());
        playerDto.setSurname(player.getSurname());
        playerDto.setName(player.getName());
        playerDto.setCareerStarted(player.getCareerStarted());
        playerDto.setNumberOnJersey(player.getNumberOnJersey());
        return playerDto;
    }

    @Override
    public Player toEntity(PlayerDto playerDto) {
        return null;
    }
}
