package com.example.betbullrestapi.repository;

import com.example.betbullrestapi.domains.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository extends PagingAndSortingRepository<Player, Long> {
    @Query("select p from Player p where p.name like %:name%")
    List<Player> findByNames(String name);
}
