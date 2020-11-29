package com.example.betbullrestapi.util;


import com.example.betbullrestapi.domain.Player;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerFeeDefinerTest {

    @Test
    public void definePlayerFeeTest(){
        //given
        LocalDate birthDate = LocalDate.of(1990,6,25);
        LocalDate careerStartedDate = LocalDate.of(2008,11,19);
        LocalDate now = LocalDate.now();

        long monthOfExperience = ChronoUnit.MONTHS.between(careerStartedDate, now);
        long age = ChronoUnit.YEARS.between(birthDate, now);

        //expected
        BigDecimal fee = BigDecimal.valueOf(monthOfExperience)
                .multiply(BigDecimal.valueOf(100_000))
                .divide(BigDecimal.valueOf(age), 0, RoundingMode.CEILING);

        assertEquals(fee, PlayerFeeDefiner.definePlayerFee(birthDate, careerStartedDate));

    }

    @Test
    public void definePlayerFeeWithCommissionTest(){
        //given
        Player player = new Player();
        player.setTransferFee(BigDecimal.valueOf(100_000));

        //expected
        BigDecimal transferFeeWithCommission = BigDecimal.valueOf(110_000);

        assertEquals(transferFeeWithCommission, PlayerFeeDefiner.definePlayerTransferFeeWithCommission(player));
    }
}
