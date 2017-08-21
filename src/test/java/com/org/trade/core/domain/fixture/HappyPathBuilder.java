package com.org.trade.core.domain.fixture;

import com.org.trade.core.domain.Direction;
import com.org.trade.core.domain.EntityType;
import com.org.trade.core.domain.TradingInstruction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.org.trade.core.domain.TradingInstruction.Builder;
import static com.org.trade.core.domain.TradingInstruction.Builder.newBuilder;

public class HappyPathBuilder {

    public static LocalDate AUGUST_FOURTEEN_2017 = LocalDate.of(2017, 8, 14);

    public static Builder buildTradingInstruction() {
        return newBuilder().withEntityType(EntityType.OIL)
                .withDirection(Direction.BUY)
                .withAgreedFxRate(new BigDecimal(".50"))
                .withCurrency("GBP")
                .withInstructionDate(LocalDate.now())
                .withSettlementDate(LocalDate.now())
                .withNumberOfUnits(10)
                .withPricePerUnit(new BigDecimal("48"));
    }

    public static List<TradingInstruction> buildTradingInstructions() {
        ArrayList<TradingInstruction> tradingInstructions = new ArrayList<>();
        tradingInstructions.addAll(buildBuyTradingInstructions(Direction.BUY));
        tradingInstructions.addAll(buildBuyTradingInstructions(Direction.SELL));
        return tradingInstructions;
    }

    private static List<TradingInstruction> buildBuyTradingInstructions(Direction direction) {
        ArrayList<TradingInstruction> tradingInstructions = new ArrayList<>();
        Builder builder = buildTradingInstruction().withSettlementDate(AUGUST_FOURTEEN_2017);
        tradingInstructions.add(builder.withDirection(direction).withEntityType(EntityType.CURRENCY).withNumberOfUnits(20).build());
        //Different entity amd differemt price for each entity
        builder = buildTradingInstruction().withSettlementDate(AUGUST_FOURTEEN_2017);
        tradingInstructions.add(builder.withDirection(direction).withEntityType(EntityType.OIL).withPricePerUnit(new BigDecimal("50")).build());

        //Different entity amd differemt agreed rate
        builder = buildTradingInstruction().withSettlementDate(AUGUST_FOURTEEN_2017);
        tradingInstructions.add(builder.withDirection(direction).withEntityType(EntityType.GOLD).withAgreedFxRate(new BigDecimal("1.2")).build());

        //Different settlement date from other instructions
        builder = buildTradingInstruction().withSettlementDate(LocalDate.now());
        tradingInstructions.add(builder.withDirection(direction).withEntityType(EntityType.GOLD).withAgreedFxRate(new BigDecimal("1.2")).build());

        return tradingInstructions;
    }
}