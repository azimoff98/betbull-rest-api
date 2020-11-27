package com.example.betbullrestapi.util;


import com.example.betbullrestapi.domain.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @author Gurban.Azimli
 * @date 2020/11/27 11:47 PM
 */
public class PlayerFeeDefiner {

    /**
     * Method defines fee for player.
     * Player fee is calculated as below
     *
     * player fee = (MonthsOfExperience * 100_000) / AGE
     */
    public static BigDecimal definePlayerFee(LocalDate birthDate, LocalDate careerStartedDate){
        LocalDate now = LocalDate.now();

        long monthOfExperience = ChronoUnit.MONTHS.between(careerStartedDate, now);
        long age = ChronoUnit.YEARS.between(birthDate, now);

        return BigDecimal.valueOf(monthOfExperience)
                .multiply(BigDecimal.valueOf(100_000))
                .divide(BigDecimal.valueOf(age), 0, RoundingMode.CEILING);

    }
}
