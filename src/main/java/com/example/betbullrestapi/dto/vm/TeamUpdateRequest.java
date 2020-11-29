package com.example.betbullrestapi.dto.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamUpdateRequest {
    @NotBlank(message = "Team name cannot be blank")
    private String teamName;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate establishmentDate;
    @Min(value = 0, message = "budget must be greater than zero")
    private BigDecimal budget;
}
