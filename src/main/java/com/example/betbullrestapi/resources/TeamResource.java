package com.example.betbullrestapi.resources;

import com.example.betbullrestapi.dto.ApiMessage;
import com.example.betbullrestapi.dto.TeamDto;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;
import com.example.betbullrestapi.mappers.TeamMapper;
import com.example.betbullrestapi.services.TeamService;
import com.example.betbullrestapi.utils.ApiBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Gurban.Azimli
 * @date 2020/11/27 9:43 PM
 *
 * TeamResource class exports API for teams.
 * Includes simple CRUD operations and transfer functionality.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/teams")
@CrossOrigin(origins = "*")
public class TeamResource implements ApiBuilder {

    private final TeamService teamService;
    private final TeamMapper teamMapper;


    @GetMapping
    public ResponseEntity<List<TeamDto>> findAll(){
        log.info("Rest request for all teams accepted");
        List<TeamDto> response = teamService.findAll()
                .stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }

    @GetMapping("/pages")
    public ResponseEntity<List<TeamDto>> findWithPagination(@RequestParam(name = "pageIndex", defaultValue = "0") Integer index,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer size){

        int from = (index * size);
        int to = ((index+1) * size);
        log.info("Rest request for teams from: {} to: {}", from, to);
        List<TeamDto> response = teamService.findWithPagination(index, size)
                .stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeamDto> findById(@PathVariable Long id){
        log.info("Rest request accepted for team with id: {}", id);
        TeamDto response = teamMapper.toDto(teamService.findById(id));
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/names")
    public ResponseEntity<List<TeamDto>> findByName(@RequestParam(name = "name") String name){
        log.info("Rest request for teams with name like: {}", name);
        List<TeamDto> response = teamService.findByName(name)
                .stream()
                .map(teamMapper::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }

    @GetMapping("/player/{playerId}")
    public ResponseEntity<TeamDto> findByPlayerId(@PathVariable Long playerId){
        log.info("Rest request for team of player with id: {}", playerId);
        TeamDto response = teamMapper.toDto(teamService.findByPlayerId(playerId));

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<ApiMessage> create(@Valid @RequestBody TeamCreationRequest request){
        log.info("Rest request for team creation accepted");
        teamService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateOkay());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessage> deleteById(@PathVariable Long id){
        log.info("Rest request for deleting team with id: {}", id);
        teamService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateNoContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiMessage> update(@Valid @RequestBody TeamUpdateRequest request, @PathVariable Long id){
        log.info("Rest request for updating team with id: {}", id);
        teamService.update(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateAccepted());
    }

    @PutMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<ApiMessage> transfer(@PathVariable(name = "teamId") Long teamId,
                                               @PathVariable(name = "playerId") Long playerId){
        log.info("Rest request to transfer player with id: {} to team with id: {}", playerId, teamId);
        teamService.transfer(teamId, playerId);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateOkay());
    }
}
