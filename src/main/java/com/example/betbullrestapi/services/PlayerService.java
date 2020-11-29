package com.example.betbullrestapi.services;

import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
import com.example.betbullrestapi.dto.vm.PlayerUpdateRequest;


import java.util.List;

public interface PlayerService {

    void create(PlayerCreationRequest request);
    List<Player> findAll();
    List<Player> findWithPagination(int pageIndex, int pageSize);
    Player findById(Long id);
    List<Player> findByName(String name);
    void deleteById(long id);
    void update(PlayerUpdateRequest request, Long id);
}
