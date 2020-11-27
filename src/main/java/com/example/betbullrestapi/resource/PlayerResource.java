package com.example.betbullrestapi.resource;

import com.example.betbullrestapi.domain.Player;
import com.example.betbullrestapi.dto.ApiMessage;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
import com.example.betbullrestapi.dto.vm.PlayerUpdateRequest;
import com.example.betbullrestapi.service.PlayerService;
import io.swagger.models.auth.In;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Gurban.Azimli
 * @date 2020/11/27 9:43 PM
 *
 * PlayerResource class exports API for players.
 * Includes simple CRUD operations.
 *
 */
@RestController
@RequestMapping("/players")
@Slf4j
@RequiredArgsConstructor
public class PlayerResource implements ApiBuilder{


    private final PlayerService playerService;


    @GetMapping
    public ResponseEntity<List<Player>> findAll(){
        log.info("Rest request for all players accepted");
        List<Player> response = playerService.findAll();

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Player> findById(@PathVariable Long id){
        log.info("Rest request for player with id: {} accepted", id);
        Player response = playerService.findById(id);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/names")
    public ResponseEntity<List<Player>> findByName(@RequestParam(name = "name") String name){
        log.info("Rest request for players with name like: {} accepted", name);
        List<Player> response = playerService.findByName(name);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @GetMapping("/pages")
    public ResponseEntity<List<Player>> findWithPagination(@RequestParam(name = "pageIndex", defaultValue = "0") Integer index,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer size){
        int from = (index * size);
        int to = ((index+1) * size);
        log.info("Rest request for teams from: {} to: {}", from, to);
        List<Player> response = playerService.findWithPagination(index, size);

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

    @PostMapping
    public ResponseEntity<ApiMessage> create(@RequestBody PlayerCreationRequest request){
        log.info("Rest request for creation player accepted");
        playerService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateOkay());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiMessage> deleteById(@PathVariable Long id){
        log.info("Rest request accepted for deleting player with id: {}", id);
        playerService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateNoContent());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiMessage> update(@RequestBody PlayerUpdateRequest request, @PathVariable Long id){
        log.info("Rest request for updating player with id: {}", id);

        playerService.update(request, id);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(generateAccepted());
    }


}