package com.example.betbullrestapi.repository;

import com.example.betbullrestapi.domain.Team;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends PagingAndSortingRepository<Team, Long> {
    @Query("select t from Team t where t.teamName like %:name%")
    List<Team> findByNames(String name);
}
