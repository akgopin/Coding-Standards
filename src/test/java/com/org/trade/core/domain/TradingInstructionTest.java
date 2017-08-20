package com.org.trade.core.domain;

import static com.org.trade.core.domain.fixture.HappyPathBuilder.buildTradingInstruction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;

public class TradingInstructionTest {

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void expect_tradingInstruction_builds(){
        buildTradingInstruction().build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_productType_is_null(){
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Product type should not be null");
        buildTradingInstruction().withProductType(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_directionType_is_null(){
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Direction should not be null");
        buildTradingInstruction().withDirection(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_rate_is_zero(){
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Agreed rate should be greater than zero");
        buildTradingInstruction().withAgreedFxRate(BigDecimal.ZERO).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_rate_is_negative(){
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Agreed rate should be greater than zero");
        buildTradingInstruction().withAgreedFxRate(new BigDecimal("-1")).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_instructionDate_is_null(){
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Instruction date should not be null");
        buildTradingInstruction().withInstructionDate(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_settlementDate_is_null(){
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Settlement date should not be null");
        buildTradingInstruction().withSettlementDate(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_number_of_units_is_negative(){
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("No of units should be greater than zero");
        buildTradingInstruction().withNoOfUnits(-1).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_number_of_units_is_zero(){
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("No of units should be greater than zero");
        buildTradingInstruction().withNoOfUnits(0).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_price_per_unit_is_null(){
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Price per unit should not be null");
        buildTradingInstruction().withPricePerUnit(null).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_price_per_unit_is_zero(){
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Price per unit should be greater than zero");
        buildTradingInstruction().withPricePerUnit(BigDecimal.ZERO).build();
    }

    @Test
    public void expect_tradingInstruction_doesnot_builds_when_price_per_unit_is_negative(){
        expectedEx.expect(IllegalStateException.class);
        expectedEx.expectMessage("Price per unit should be greater than zero");
        buildTradingInstruction().withPricePerUnit(new BigDecimal("-1")).build();
    }
}
