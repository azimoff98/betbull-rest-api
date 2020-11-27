package com.example.betbullrestapi.service.impl;

import com.example.betbullrestapi.domain.Player;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
import com.example.betbullrestapi.dto.vm.PlayerUpdateRequest;
import com.example.betbullrestapi.exception.DomainNotFoundException;
import com.example.betbullrestapi.repository.PlayerRepository;
import com.example.betbullrestapi.service.PlayerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;

    @Override
    public void create(PlayerCreationRequest request) {

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
