package com.example.betbullrestapi.repository;

import com.example.betbullrestapi.domains.Team;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {
    @Query("select t from Team t where t.teamName like %:name%")
    List<Team> findByNames(String name);
    @Query("select new Team(t.id, t.teamName, t.establishmentDate, t.budget) from Team t join  t.players p where p.id = :playerId")
    Team findByPlayerId(Long playerId);
}
