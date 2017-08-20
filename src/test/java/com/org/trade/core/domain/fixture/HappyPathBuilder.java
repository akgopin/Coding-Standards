package com.org.trade.core.domain.fixture;

import com.org.trade.core.domain.Direction;
import com.org.trade.core.domain.ProductType;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.org.trade.core.domain.TradingInstruction.Builder;
import static com.org.trade.core.domain.TradingInstruction.Builder.newBuilder;

public class HappyPathBuilder {

    public static Builder buildTradingInstruction() {
       return newBuilder().withProductType(ProductType.OIL)
                .withDirection(Direction.BUY)
                .withAgreedFxRate(new BigDecimal(".50"))
                .withCurrency("GBP")
                .withInstructionDate(LocalDate.now())
                .withSettlementDate(LocalDate.now())
                .withNoOfUnits(10)
                .withPricePerUnit(new BigDecimal("48"));
    }
}
