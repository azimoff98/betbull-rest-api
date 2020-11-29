package com.example.betbullrestapi.services.impl;

import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.services.FeeDefinerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
@Slf4j
public class FeeDefinerServiceImpl implements FeeDefinerService {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    /**
     * Method defines fee for player.
     * Player fee is calculated as below
     *
     * player fee = (MonthsOfExperience * 100_000) / AGE
     */
    @Override
    public BigDecimal definePlayerFee(LocalDate birthDate, LocalDate careerStartedDate) {
            LocalDate now = LocalDate.now();

            long monthOfExperience = ChronoUnit.MONTHS.between(careerStartedDate, now);
            long age = ChronoUnit.YEARS.between(birthDate, now);

            return BigDecimal.valueOf(monthOfExperience)
                    .multiply(BigDecimal.valueOf(100_000))
                    .divide(BigDecimal.valueOf(age), 0, RoundingMode.CEILING);


    }

    /**
     * Commission is 10% of player's transfer fee.
     */
    @Override
    public BigDecimal definePlayerFeeWithCommission(Player player) {
        BigDecimal playerFee = player.getTransferFee();
        return playerFee.multiply(BigDecimal.valueOf(110)).divide(ONE_HUNDRED, 0, RoundingMode.CEILING);
    }
}
