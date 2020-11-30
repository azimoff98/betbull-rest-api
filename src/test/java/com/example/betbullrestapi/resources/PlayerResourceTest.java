package com.example.betbullrestapi.resources;

import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.dto.ApiMessage;
import com.example.betbullrestapi.dto.PlayerDto;
import com.example.betbullrestapi.dto.vm.PlayerCreationRequest;
import com.example.betbullrestapi.dto.vm.PlayerUpdateRequest;
import com.example.betbullrestapi.exceptions.DomainNotFoundException;
import com.example.betbullrestapi.mappers.PlayerCreationRequestMapper;
import com.example.betbullrestapi.mappers.PlayerMapper;
import com.example.betbullrestapi.mappers.PlayerMapperTest;
import com.example.betbullrestapi.services.PlayerService;
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
import java.time.LocalDateTime;
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
 * @date 2020/11/28 11:03 PM
 */
@WebMvcTest(PlayerResource.class)
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
public class PlayerResourceTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayerService playerService;
    @MockBean
    private PlayerMapper playerMapper;
    @MockBean
    private PlayerCreationRequestMapper playerCreationRequestMapper;
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void findAllTest() {
        //expected
        List<Player> result = new ArrayList<Player>() {{
            add(
                    new Player(1L, "test name", "test surname", "10",
                            LocalDate.of(2015, 5, 10), LocalDate.of(1995, 10, 15),
                            BigDecimal.valueOf(100_000), null)
            );
            add(
                    new Player(2L, "test name 2", "test surname 2", "20",
                            LocalDate.of(2018, 5, 10), LocalDate.of(1993, 10, 15),
                            BigDecimal.valueOf(50_000), null)
            );
        }};
        final List<PlayerDto> converted = result.stream().map(playerMapper::toDto).collect(Collectors.toList());
        final String expected = objectMapper.writeValueAsString(converted);

        given(playerService.findAll()).willReturn(result);

        mockMvc.perform(
                get("/players")
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(expected)
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void findByIdTest() {
        //given
        final Long id = 5L;

        //expected
        Player player = new Player(5L, "test name", "test surname", "10",
                LocalDate.of(2015, 5, 10), LocalDate.of(1995, 10, 15),
                BigDecimal.valueOf(100_000), null);

        final PlayerDto converted = new PlayerDto(5L, "test name", "test surname", "10",
                LocalDate.of(2015, 5, 10), LocalDate.of(1995, 10, 15),
                BigDecimal.valueOf(100_000));
        final String expected = objectMapper.writeValueAsString(converted);

        given(playerService.findById(id)).willReturn(player);
        given(playerMapper.toDto(player)).willReturn(converted);
        System.out.println("domain: " + player);
        System.out.println("dto: " + converted);
        mockMvc.perform(
                get("/players/{id}", id)

        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(expected)
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void findByNamesTest() {
        //given
        final String name = "test";

        //expected
        List<Player> result = new ArrayList<Player>() {{
            add(
                    new Player(1L, "test name", "test surname", "10",
                            LocalDate.of(2015, 5, 10), LocalDate.of(1995, 10, 15),
                            BigDecimal.valueOf(100_000), null)
            );
            add(
                    new Player(2L, "test name 2", "test surname 2", "20",
                            LocalDate.of(2018, 5, 10), LocalDate.of(1993, 10, 15),
                            BigDecimal.valueOf(50_000), null)
            );
        }};
        List<PlayerDto> converted = result.stream().map(playerMapper::toDto).collect(Collectors.toList());
        final String expected = objectMapper.writeValueAsString(converted);

        given(playerService.findByName(name)).willReturn(result);

        mockMvc.perform(
                get("/players/names")
                        .param("name", name)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(expected)
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void findWithPaginationTest() {
        //given
        final int pageIndex = 1;
        final int pageSize = 10;

        //expected
        final List<Player> result = new ArrayList<Player>() {{
            add(
                    new Player(1L, "test name", "test surname", "10",
                            LocalDate.of(2015, 5, 10), LocalDate.of(1995, 10, 15),
                            BigDecimal.valueOf(100_000), null)
            );
            add(
                    new Player(2L, "test name 2", "test surname 2", "20",
                            LocalDate.of(2018, 5, 10), LocalDate.of(1993, 10, 15),
                            BigDecimal.valueOf(50_000), null)
            );
        }};
        final List<PlayerDto> converted = result.stream().map(playerMapper::toDto).collect(Collectors.toList());
        final String expected = objectMapper.writeValueAsString(converted);

        given(playerService.findWithPagination(pageIndex, pageSize)).willReturn(result);

        mockMvc.perform(
                get("/players/pages")
                        .param("pageIndex", String.valueOf(pageIndex))
                        .param("pageSize", String.valueOf(pageSize))
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(expected)
        ).andDo(
                print()
        );

    }

    @SneakyThrows
    @Test
    public void createTestCreated() {
        //given
        PlayerCreationRequest request = new PlayerCreationRequest();
        request.setBirthDate(LocalDate.of(2001, 10, 15));
        request.setCareerStarted(LocalDate.of(2019, 2, 6));
        request.setName("Test name");
        request.setSurname("Test surname");
        request.setNumberOnJersey("10");
        request.setTeamId(1L);

        final String given = objectMapper.writeValueAsString(request);

        Player player = playerCreationRequestMapper.toEntity(request);

        //expected
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setDate(LocalDateTime.now());
        apiMessage.setMessage("API call finished successfully");

        final String expected = objectMapper.writeValueAsString(apiMessage);


        doNothing().when(playerService).create(request);

        mockMvc.perform(
                post("/players").contentType(MediaType.APPLICATION_JSON)
                        .content(given)
        ).andExpect(
                status().isCreated()
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void createTestBadRequest(){{
        //given
        PlayerCreationRequest request = new PlayerCreationRequest();
        request.setBirthDate(LocalDate.of(2001, 10, 15));
        request.setCareerStarted(LocalDate.of(2019, 2, 6));
        request.setName("");
        request.setSurname("Test surname");
        request.setNumberOnJersey("10");
        request.setTeamId(1L);

        final String given = objectMapper.writeValueAsString(request);

        Player player = playerCreationRequestMapper.toEntity(request);

        //expected
        ApiMessage apiMessage = new ApiMessage();
        apiMessage.setDate(LocalDateTime.now());
        apiMessage.setMessage("API call finished successfully");

        final String expected = "{\"name\":\"Name of player cannot be blank\"}";


        doNothing().when(playerService).create(request);

        mockMvc.perform(
                post("/players").contentType(MediaType.APPLICATION_JSON)
                        .content(given)
        ).andExpect(
                status().isBadRequest()
        ).andExpect(
            content().string(expected)
        ).andDo(
                print()
        );
    }

    }

    @SneakyThrows
    @Test
    public void deleteByIdTestDeleted(){
        //given
        final long id = 5;

        //expected
        doNothing().when(playerService).deleteById(id);

        mockMvc.perform(
                delete("/players/{id}", id)
        ).andExpect(
                status().isNoContent()
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void deleteByIdTestNotFound(){
        //given
        final long id = 5;

        //expected
        final String exceptionMessage = "No player found to update with id: " + id;

        doThrow(new DomainNotFoundException("No player found to update with id: " + id)).when(playerService).deleteById(id);

        mockMvc.perform(
                delete("/players/{id}", id)
        ).andExpect(
                status().isNotFound()
        ).andDo(
                print()
        );
    }

    @SneakyThrows
    @Test
    public void updateTestAccepted(){
        //given
        final PlayerUpdateRequest request = new PlayerUpdateRequest();
        request.setBirthDate(LocalDate.of(2000,10,20));
        request.setCareerStarted(LocalDate.of(2020,10,20));
        request.setName("Test name");
        request.setSurname("Test surname");
        request.setNumberOnJersey("10");
        request.setTeamId(5L);
        request.setTransferFee(BigDecimal.valueOf(500));

        final long playerId = 5;

        final String contentBody = objectMapper.writeValueAsString(request);



        doNothing().when(playerService).update(request, playerId);

        mockMvc.perform(
                put("/players/{id}", playerId)
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
    public void updateBadRequest(){
        //given
        final PlayerUpdateRequest request = new PlayerUpdateRequest();
        request.setBirthDate(LocalDate.of(2000,10,20));
        request.setCareerStarted(LocalDate.of(2020,10,20));
        request.setName("");
        request.setSurname("");
        request.setNumberOnJersey("10");
        request.setTeamId(5L);
        request.setTransferFee(BigDecimal.valueOf(500));
        final String contentBody = objectMapper.writeValueAsString(request);
        final long playerId = 5;

        //expected
        final String exceptionMessage = "{\"surname\":\"Player surname cannot be blank\",\"name\":\"Player name cannot be blank\"}";

        doNothing().when(playerService).update(request, playerId);


        mockMvc.perform(
                put("/players/{id}", playerId)
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



}
