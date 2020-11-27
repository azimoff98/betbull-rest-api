package com.example.betbullrestapi.resource;

import com.example.betbullrestapi.domain.Team;
import com.example.betbullrestapi.domain.TeamPlayer;
import com.example.betbullrestapi.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/teams")
public class TeamResource {

    private final TeamService teamService;


    @GetMapping
    public ResponseEntity<List<Team>> findAll(){
        log.info("Rest request for all teams accepted");
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(Collections.singletonList(new Team()));

    }

}
