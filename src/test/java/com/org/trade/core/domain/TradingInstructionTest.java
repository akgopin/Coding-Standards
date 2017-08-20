package com.org.trade.core.domain;

import static com.org.trade.core.domain.fixture.HappyPathBuilder.buildTradingInstruction;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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


}
