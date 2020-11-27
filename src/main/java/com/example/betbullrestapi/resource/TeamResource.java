package com.example.betbullrestapi.resource;

import com.example.betbullrestapi.domain.Team;
import com.example.betbullrestapi.dto.ApiMessage;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;
import com.example.betbullrestapi.service.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/teams")
@CrossOrigin(origins = "*")
public class TeamResource implements ApiBuilder{

    private final TeamService teamService;



    @GetMapping
    public ResponseEntity<List<Team>> findAll(){
        log.info("Rest request for all teams accepted");
        List<Team> response = teamService.findAll();
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }

    @GetMapping("/pages")
    public ResponseEntity<List<Team>> findWithPagination(@RequestParam(name = "pageIndex", defaultValue = "0") Integer index,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer size){

        int from = (index * size);
        int to = ((index+1) * size);
        log.info("Rest request for teams from: {} to: {}", from, to);
        List<Team> response = teamService.findWithPagination(index, size);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> findById(@PathVariable Long id){
        log.info("Rest request accepted for team with id: {}", id);
        Team response = teamService.findById(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Team>> findByName(@RequestParam(name = "name") String name){
        log.info("Rest request for teams with name like: {}", name);
        List<Team> response = teamService.findByName(name);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }

    @PostMapping
    public ResponseEntity<ApiMessage> save(@Valid @RequestBody TeamCreationRequest request){
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
    public ResponseEntity<ApiMessage> update(@RequestBody TeamUpdateRequest request, @PathVariable Long id){
        log.info("Rest request for updating team with id: {}", id);
        teamService.update(request, id);

        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateAccepted());
    }
}
