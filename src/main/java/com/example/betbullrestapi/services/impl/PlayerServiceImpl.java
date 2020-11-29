package com.example.betbullrestapi.services.impl;

import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.domains.Team;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
import com.example.betbullrestapi.dto.vm.PlayerUpdateRequest;
import com.example.betbullrestapi.exceptions.DomainNotFoundException;
import com.example.betbullrestapi.mappers.PlayerCreationRequestMapper;
import com.example.betbullrestapi.repository.PlayerRepository;
import com.example.betbullrestapi.repository.TeamRepository;
import com.example.betbullrestapi.services.FeeDefinerService;
import com.example.betbullrestapi.services.PlayerService;
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
    private final FeeDefinerService feeDefinerService;
    private final PlayerCreationRequestMapper playerCreationRequestMapper;

    @Override
    public void create(PlayerCreationRequest request) {
        //first step calculate fee for player
        Player player = playerCreationRequestMapper.toEntity(request);
        player.setTransferFee(
                feeDefinerService.definePlayerFee(player.getBirthDate(), player.getCareerStarted())
        );
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
        log.info("Player updating process started");
        Player player = playerRepository.findById(id)
                .orElseThrow(() -> new DomainNotFoundException("No player found to update with id: " + id));

        playerRepository.save(playerUpdater(player, request));
    }

    /**
     * Every non-null field of request will be set to player with provided id.
     * As transfer fee depends on months of career and age og player
     * when changer are applied to player's careerStartedDate and birthDate
     * transferFee will also change.
     *
     * @param player fetched player to update
     * @param request incoming request
     * @return updated player
     */
    private Player playerUpdater(Player player, PlayerUpdateRequest request){
        if(request.getBirthDate() != null){
            player.setBirthDate(request.getBirthDate());
            player.setTransferFee(
                    feeDefinerService.definePlayerFee(request.getBirthDate(), player.getCareerStarted())
            );
        }
        if(request.getCareerStarted() != null) {
            player.setCareerStarted(request.getCareerStarted());
            player.setTransferFee(
                    feeDefinerService.definePlayerFee(player.getBirthDate(), request.getCareerStarted())
            );
        }
        if(request.getName() != null)
            player.setName(request.getName());
        if(request.getSurname() != null)
            player.setSurname(request.getSurname());
        if(request.getNumberOnJersey() != null)
            player.setNumberOnJersey(request.getNumberOnJersey());
        if(request.getTransferFee() != null)
            player.setTransferFee(request.getTransferFee());
        if(request.getTeamId() != null){
            Team team = teamRepository.findById(request.getTeamId())
                    .orElseThrow(() -> new DomainNotFoundException("No team found with id: " + request.getTeamId()));
            player.setTeam(team);
        }
        return player;
    }
}
