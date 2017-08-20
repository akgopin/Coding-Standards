package com.org.trade.core.reporting;

import com.org.trade.core.domain.Direction;
import com.org.trade.core.domain.TradingInstruction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

/*
*
*  The class generates different reports base on the trading for a given date
*
*/
public class ReportGenerator {

    private final List<TradingInstruction> tradingInstructions;

    private ReportGenerator(final List<TradingInstruction> tradingInstructions) {
        checkNotNull(tradingInstructions, "Trading instruction list should not be null when building %s", this.getClass());
        this.tradingInstructions = tradingInstructions;
    }

    public static ReportGenerator of(final List<TradingInstruction> tradingInstructions) {
        return new ReportGenerator(tradingInstructions);
    }

    /*
    *  Calculates the total amount traded for incoming transactions based on settled date
    *
    *  If no incoming transactions then zero will be returned
    *
    */
    public BigDecimal calculateTotalOfIncomingTransactions(final LocalDate settledDate) {
        return tradingInstructions.stream()
                .filter(tradingInstruction -> tradingInstruction.getDirection() == Direction.SELL)
                .filter(tradingInstruction -> tradingInstruction.getSettlementDate() == settledDate)
                .map(tradingInstruction -> tradingInstruction.determineTradeAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    /*
    *  Calculates the total amount traded for outgoing transactions based on settled date
    *
    *  If no outgoing transactions then zero will be returned
    *
    */
    public BigDecimal calculateTotalOfOutgoingTransactions(final LocalDate settledDate) {
        return tradingInstructions.stream()
                .filter(tradingInstruction -> tradingInstruction.getDirection() == Direction.BUY)
                .filter(tradingInstruction -> tradingInstruction.getSettlementDate() == settledDate)
                .map(tradingInstruction -> tradingInstruction.determineTradeAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    /*
     *  Returns the list of Incoming tradings on ascending order of trading amount.
     */
    public List<TradingInstruction> sortIncomingTradingByAmount(final LocalDate settledDate) {
        return tradingInstructions.stream()
                .filter(tradingInstruction -> tradingInstruction.getDirection() == Direction.SELL)
                .filter(tradingInstruction -> tradingInstruction.getSettlementDate() == settledDate)
                .sorted((ins1, ins2) -> ins1.determineTradeAmount().compareTo(ins2.determineTradeAmount()))
                .collect(Collectors.toList());
    }

    /*
     *  Returns the list of outgoing tradings on ascending order of trading amount.
     */
    public List<TradingInstruction> sortOutgoingTradingByAmount(final LocalDate settledDate) {
        return tradingInstructions.stream()
                .filter(tradingInstruction -> tradingInstruction.getDirection() == Direction.BUY)
                .filter(tradingInstruction -> tradingInstruction.getSettlementDate() == settledDate)
                .sorted((ins1, ins2) -> ins1.determineTradeAmount().compareTo(ins2.determineTradeAmount()))
                .collect(Collectors.toList());
    }
}
