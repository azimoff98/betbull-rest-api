package com.example.betbullrestapi.util;


import com.example.betbullrestapi.domain.Player;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Non-instantiable class PlayerFeeDefiner.
 *
 * @author Gurban.Azimli
 * @date 2020/11/27 11:47 PM
 */
public final class PlayerFeeDefiner {

    private static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

    private PlayerFeeDefiner(){ }

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

    public static BigDecimal definePlayerTransferFeeWithCommission(Player player){
        BigDecimal playerFee = player.getTransferFee();
        return playerFee.multiply(BigDecimal.valueOf(110)).divide(ONE_HUNDRED, 0, RoundingMode.CEILING);
    }
}
