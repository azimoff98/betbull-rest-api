package com.example.betbullrestapi.dto.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.Min;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class TeamUpdateRequest {
    private String teamName;
    @JsonFormat(pattern = "yyyy/MM/dd")
    private LocalDate establishmentDate;
    @Min(value = 0, message = "budget must be greater than zero")
    private BigDecimal budget;
}
