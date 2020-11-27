package com.example.betbullrestapi.service.impl;

import com.example.betbullrestapi.domain.Player;
import com.example.betbullrestapi.domain.Team;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
import com.example.betbullrestapi.dto.vm.PlayerUpdateRequest;
import com.example.betbullrestapi.exception.DomainNotFoundException;
import com.example.betbullrestapi.mapper.PlayerCreationRequestMapper;
import com.example.betbullrestapi.repository.PlayerRepository;
import com.example.betbullrestapi.repository.TeamRepository;
import com.example.betbullrestapi.service.PlayerService;
import com.example.betbullrestapi.util.PlayerFeeDefiner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Gurban.Azimli
 * @date 2020/11/27 10:46 PM
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PlayerCreationRequestMapper playerCreationRequestMapper;

    @Override
    public void create(PlayerCreationRequest request) {
        //first step calculate fee for player
        Player player = playerCreationRequestMapper.toEntity(request);
        player.setTransferFee(PlayerFeeDefiner.definePlayerFee(player.getBirthDate(), player.getCareerStarted()));
        //second step fetching team for player
        Team team = teamRepository.findById(request.getTeamId()).orElseThrow(
                () -> new DomainNotFoundException("Provided id is wrong for team. No team found for adding player")
        );
        player.setTeam(team);

        playerRepository.save(player);

    }

    @Override
    public List<Player> findAll() {
        return (List<Player>) playerRepository.findAll();
    }

    @Override
    public List<Player> findWithPagination(int pageIndex, int pageSize) {
        return playerRepository.findAll(PageRequest.of(pageIndex, pageSize)).toList();
    }

    @Override
    public Player findById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException("No player found with id: "+id));
    }

    @Override
    public List<Player> findByName(String name) {
        return playerRepository.findByNames(name);
    }

    @Override
    public void deleteById(long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public void update(PlayerUpdateRequest request, Long id) {

    }
}
