package com.org.trade.core.domain;

import com.org.trade.core.domain.util.DateUtil;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.org.trade.core.domain.fixture.HappyPathBuilder.buildTradingInstruction;

public class TradingInstructionTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void expect_tradingInstruction_builds() {
        buildTradingInstruction().build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_productType_is_null() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Entity type should not be null");
        buildTradingInstruction().withEntityType(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_directionType_is_null() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Direction should not be null");
        buildTradingInstruction().withDirection(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_rate_is_zero() {
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Agreed rate should be greater than zero");
        buildTradingInstruction().withAgreedFxRate(BigDecimal.ZERO).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_rate_is_negative() {
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Agreed rate should be greater than zero");
        buildTradingInstruction().withAgreedFxRate(new BigDecimal("-1")).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_instructionDate_is_null() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Instruction date should not be null");
        buildTradingInstruction().withInstructionDate(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_settlementDate_is_null() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Settlement date should not be null");
        buildTradingInstruction().withSettlementDate(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_number_of_units_is_negative() {
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("No of units should be greater than zero");
        buildTradingInstruction().withNumberOfUnits(-1).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_number_of_units_is_zero() {
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("No of units should be greater than zero");
        buildTradingInstruction().withNumberOfUnits(0).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_price_per_unit_is_null() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Price per unit should not be null");
        buildTradingInstruction().withPricePerUnit(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_price_per_unit_is_zero() {
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Price per unit should be greater than zero");
        buildTradingInstruction().withPricePerUnit(BigDecimal.ZERO).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_price_per_unit_is_negative() {
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Price per unit should be greater than zero");
        buildTradingInstruction().withPricePerUnit(new BigDecimal("-1")).build();
    }

    @Test
    public void expect_settlement_date_pushed_considering_saturday_sunday_for_currencies_other_than_SAR_and_AED() {
        LocalDate actualDateOnSunday = LocalDate.of(2017, 8, 13);
        LocalDate calculatedDateOnMonday = LocalDate.of(2017, 8, 14);
        TradingInstruction tradingInstruction = buildTradingInstruction().withSettlementDate(actualDateOnSunday)
                .withCurrency("USD")
                .build();
        assertThat(calculatedDateOnMonday, is(tradingInstruction.getSettlementDate()));

        LocalDate actualDateOnSaturday = LocalDate.of(2017, 8, 12);
        tradingInstruction = buildTradingInstruction().withSettlementDate(actualDateOnSaturday)
                .withCurrency("USD")
                .build();
        assertThat(calculatedDateOnMonday, is(tradingInstruction.getSettlementDate()));

    }

    @Test
    public void expect_settlement_date_pushed_considering_saturday_sunday_for_currencies_for_SAR() {
        LocalDate actualDateOnFriday = LocalDate.of(2017, 8, 11);
        LocalDate calculatedDateOnSunday = LocalDate.of(2017, 8, 13);
        TradingInstruction tradingInstruction = buildTradingInstruction().withSettlementDate(actualDateOnFriday)
                .withCurrency("SAR")
                .build();
        assertThat(calculatedDateOnSunday, is(tradingInstruction.getSettlementDate()));

        LocalDate actualDateOnSaturday = LocalDate.of(2017, 8, 12);
        tradingInstruction = buildTradingInstruction().withSettlementDate(actualDateOnSaturday)
                .withCurrency("SAR")
                .build();
        assertThat(calculatedDateOnSunday, is(tradingInstruction.getSettlementDate()));

    }

    @Test
    public void expect_settlement_date_pushed_considering_saturday_sunday_for_currencies_for_AED() {
        LocalDate actualDateOnFriday = LocalDate.of(2017, 8, 11);
        LocalDate calculatedDateOnSunday = LocalDate.of(2017, 8, 13);
        TradingInstruction tradingInstruction = buildTradingInstruction().withSettlementDate(actualDateOnFriday)
                .withCurrency("AED")
                .build();
        assertThat(calculatedDateOnSunday, is(tradingInstruction.getSettlementDate()));

        LocalDate actualDateOnSaturday = LocalDate.of(2017, 8, 12);
        tradingInstruction = buildTradingInstruction().withSettlementDate(actualDateOnSaturday)
                .withCurrency("AED")
                .build();
        assertThat(calculatedDateOnSunday, is(tradingInstruction.getSettlementDate()));

    }

    @Test
    public void expect_USD_equivalent_of_trade_calculated() {
        BigDecimal tradeAmount = buildTradingInstruction().build().determineTradeAmount();
        assertThat(tradeAmount, is(new BigDecimal("240.00")));
    }
}
