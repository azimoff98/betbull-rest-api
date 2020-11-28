package com.example.betbullrestapi.dto.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
public class PlayerCreationRequest {
    @NotBlank(message = "Name of player cannot be blank")
    private String name;
    @NotBlank(message = "Surname of player cannot be blank")
    private String surname;
    @NotBlank(message = "Number on jersey cannot be blank")
    private String numberOnJersey;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate careerStarted;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;
    @Min(value = 1, message = "Team id cannot be negative")
    private Long teamId;
}
