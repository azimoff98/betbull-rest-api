package com.example.betbullrestapi.dto.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PlayerUpdateRequest {
    @NotBlank(message = "Player name cannot be blank")
    private String name;
    @NotBlank(message = "Player surname cannot be blank")
    private String surname;
    @NotBlank(message = "Number on jersey cannot be blank")
    private String numberOnJersey;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate careerStarted;
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate birthDate;
    @Min(value = 0, message = "Transfer fee cannot be negative")
    private BigDecimal transferFee;
    @Min(value = 1, message = "Team id cannot be less than one")
    private Long teamId;
}
