package com.example.betbullrestapi.resource;

import com.example.betbullrestapi.domain.Team;
import com.example.betbullrestapi.dto.TeamDto;
import com.example.betbullrestapi.dto.vm.TeamCreationRequest;
import com.example.betbullrestapi.dto.vm.TeamUpdateRequest;
import com.example.betbullrestapi.exception.DomainNotFoundException;
import com.example.betbullrestapi.exception.TransferException;
import com.example.betbullrestapi.mapper.TeamMapper;
import com.example.betbullrestapi.service.TeamService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

/**
 * @author Gurban.Azimli
 */
@WebMvcTest(TeamResource.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class TeamResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private TeamService teamService;
    @MockBean
    private TeamMapper teamMapper;


    @SneakyThrows
    @Test
    public void findAllTest(){
        //expected
        final List<Team> result = new ArrayList<Team>(){{
            add(new Team(1L, "Test Team", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null));
            add(new Team(2L, "Test Team 2", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null));
        }};
        final List<TeamDto> converted = result.stream().map(teamMapper::toDto).collect(Collectors.toList());
        final String content = objectMapper.writeValueAsString(converted);

        given(teamService.findAll()).willReturn(result);

        mockMvc.perform(
                get("/teams")
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(content)
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void findWithPaginationTest(){
        //given
        final int pageIndex = 1;
        final int pageSize = 10;

        //expected
        final List<Team> result = new ArrayList<Team>(){{
            add(new Team(1L, "Test Team", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null));
            add(new Team(2L, "Test Team 2", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null));
        }};
        final List<TeamDto> converted = result.stream().map(teamMapper::toDto).collect(Collectors.toList());
        final String content = objectMapper.writeValueAsString(converted);

        given(teamService.findWithPagination(pageIndex, pageSize)).willReturn(result);

        mockMvc.perform(
                get("/teams/pages")
                        .param("pageIndex", String.valueOf(pageIndex))
                .param("pageSize", String.valueOf(pageSize))
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(content)
        ).andDo(
                print()
        );

    }

    @SneakyThrows
    @Test
    public void findByIdTest(){
        //given
        final long id = 5;

        //expected
        final Team team = new Team(1L, "Test Team", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null);
        final TeamDto converted = new TeamDto(1L, "Test Team", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null);
        final String content = objectMapper.writeValueAsString(converted);

        given(teamService.findById(id)).willReturn(team);
        given(teamMapper.toDto(team)).willReturn(converted);

        mockMvc.perform(
                get("/teams/{id}", id)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(content)
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void findByIdTestNotFound(){
        //given
        final long id = 5;

        doThrow(new DomainNotFoundException("No team found with id: " + id)).when(teamService).findById(id);

        mockMvc.perform(
                get("/teams/{id}", id)
        ).andExpect(
                status().isNotFound()
        ).andDo(
                print()
        );

    }


    @SneakyThrows
    @Test
    public void findByNameTest(){
        //given
        final String name = "test";

        //expected
        //expected
        final List<Team> result = new ArrayList<Team>(){{
            add(new Team(1L, "Test Team", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null));
            add(new Team(2L, "Test Team 2", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null));
        }};
        final List<TeamDto> converted = result.stream().map(teamMapper::toDto).collect(Collectors.toList());
        final String content = objectMapper.writeValueAsString(converted);

        given(teamService.findByName(name)).willReturn(result);

        mockMvc.perform(
                get("/teams/names")
                .param("name", name)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(content)
        ).andDo(
                print()
        );

    }

    @SneakyThrows
    @Test
    public void findByPlayerIdTest(){
        //given
        final long playerId = 5;

        //expect
        final Team team = new Team(1L, "Test Team", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null);
        final TeamDto converted = new TeamDto(1L, "Test Team", LocalDate.of(2020,11,28), BigDecimal.valueOf(500), null);
        final String content = objectMapper.writeValueAsString(converted);


        given(teamService.findByPlayerId(playerId)).willReturn(team);
        given(teamMapper.toDto(team)).willReturn(converted);

        mockMvc.perform(
                get("/teams/player/{playerId}", playerId)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(content)
        ).andDo(
                print()
        );

    }

    @SneakyThrows
    @Test
    public void saveTestCreated(){
        //given
        final TeamCreationRequest request = new TeamCreationRequest("Test Team", LocalDate.of(2020,11,28), BigDecimal.valueOf(1000));
        final String contentBody = objectMapper.writeValueAsString(request);

        doNothing().when(teamService).create(request);

        mockMvc.perform(
                post("/teams")
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentBody)
        ).andExpect(
                status().isCreated()
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void saveTestBadRequest(){
        //given
        final TeamCreationRequest request = new TeamCreationRequest("", LocalDate.of(2020,11,28), BigDecimal.valueOf(1000));
        final String contentBody = objectMapper.writeValueAsString(request);

        //expected
        final String exceptionMessage = "{\"teamName\":\"Team name cannot be blank\"}";
        doNothing().when(teamService).create(request);

        mockMvc.perform(
                post("/teams")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody)
        ).andExpect(
                status().isBadRequest()
        ).andExpect(
                content().string(exceptionMessage)
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void deleteByIdTest(){
        //given
        final long teamId = 5;

        doNothing().when(teamService).deleteById(teamId);

        mockMvc.perform(
                delete("/teams/{teamId}", teamId)
        ).andExpect(
                status().isNoContent()
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void updateTestAccepted(){
        //given
        final long teamId = 5;
        final TeamUpdateRequest request = new TeamUpdateRequest("Test Team update", LocalDate.of(2020,10,10), BigDecimal.valueOf(1000));
        final String contentBody = objectMapper.writeValueAsString(request);

        doNothing().when(teamService).update(request, teamId);

        mockMvc.perform(
                put("/teams/{teamId}", teamId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(contentBody)
        ).andExpect(
                status().isAccepted()
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void updateTestNotFound(){
        //given
        final long teamId = 5;
        final TeamUpdateRequest request = new TeamUpdateRequest("Test Team update", LocalDate.of(2020,10,10), BigDecimal.valueOf(1000));
        final String contentBody = objectMapper.writeValueAsString(request);


        //expected
        final String exceptionMessage = "Team not found with id: " + teamId;
        doThrow(new DomainNotFoundException("Team not found with id: "+teamId)).when(teamService).update(request, teamId);



        mockMvc.perform(
                put("/teams/{teamId}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody)
        ).andExpect(
                status().isNotFound()
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void updateTestBadRequest(){
        //given
        final long teamId = 5;
        final TeamUpdateRequest request = new TeamUpdateRequest("", LocalDate.of(2020,10,10), BigDecimal.valueOf(1000));
        final String contentBody = objectMapper.writeValueAsString(request);


        //expected
        final String exceptionMessage = "{\"teamName\":\"Team name cannot be blank\"}";

        doNothing().when(teamService).update(request, teamId);



        mockMvc.perform(
                put("/teams/{teamId}", teamId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(contentBody)
        ).andExpect(
                status().isBadRequest()
        ).andExpect(
                content().string(exceptionMessage)
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void transferTestOkay(){
        //given
        final long playerId = 5;
        final long teamId = 1;


        doNothing().when(teamService).transfer(teamId, playerId);

        mockMvc.perform(
                put("/teams/{teamId}/players/{playerId}", teamId, playerId)
        ).andExpect(
                status().isOk()
        ).andDo(
                print()
        );

    }


    @SneakyThrows
    @Test
    public void transferTestNotFound(){
        //given
        final long playerId = 5;
        final long teamId = 1;


        doThrow(new DomainNotFoundException("No Team found with id: " + teamId)).when(teamService).transfer(teamId, playerId);

        mockMvc.perform(
                put("/teams/{teamId}/players/{playerId}", teamId, playerId)
        ).andExpect(
                status().isNotFound()
        ).andDo(
                print()
        );

    }

    @SneakyThrows
    @Test
    public void transferTestInternalServerError(){
        //given
        final long playerId = 5;
        final long teamId = 1;


        doThrow(new TransferException("Player with id: " + playerId + " is already team member")).when(teamService).transfer(teamId, playerId);

        mockMvc.perform(
                put("/teams/{teamId}/players/{playerId}", teamId, playerId)
        ).andExpect(
                status().isInternalServerError()
        ).andDo(
                print()
        );

    }

}
