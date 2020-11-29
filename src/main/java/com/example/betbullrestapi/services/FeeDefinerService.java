package com.example.betbullrestapi.services;

import com.example.betbullrestapi.domains.Player;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface FeeDefinerService {
    BigDecimal definePlayerFee(LocalDate birthDate, LocalDate careerStartedDate);
    BigDecimal definePlayerFeeWithCommission(Player player);
}
