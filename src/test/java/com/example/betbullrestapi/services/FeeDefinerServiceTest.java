package com.example.betbullrestapi.services;


import com.example.betbullrestapi.domains.Player;
import com.example.betbullrestapi.services.impl.FeeDefinerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class FeeDefinerServiceTest {

    @InjectMocks
    private FeeDefinerServiceImpl feeDefinerService;


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

        //response
        BigDecimal responseFee = feeDefinerService.definePlayerFee(birthDate, careerStartedDate);

        assertEquals(fee, responseFee);

    }

    @Test
    public void definePlayerFeeWithCommissionTest(){
        //given
        Player player = new Player();
        player.setTransferFee(BigDecimal.valueOf(100_000));

        //expected
        BigDecimal transferFeeWithCommission = BigDecimal.valueOf(110_000);

        //response
        BigDecimal responseFee = feeDefinerService.definePlayerFeeWithCommission(player);

        assertEquals(transferFeeWithCommission, responseFee);
    }
}
