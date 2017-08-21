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
        return calculateTotal(settledDate, Direction.SELL);
    }

    /*
    *  Calculates the total amount traded for outgoing transactions based on settled date
    *
    *  If no outgoing transactions then zero will be returned
    *
    */
    public BigDecimal calculateTotalOfOutgoingTransactions(final LocalDate settledDate) {
        return calculateTotal(settledDate, Direction.BUY);
    }

    /*
     *  Returns the list of Incoming tradings on ascending order of trading amount.
     */
    public List<TradingInstruction> sortIncomingTradingByAmount(final LocalDate settledDate) {
        return sort(settledDate, Direction.SELL);
    }

    /*
     *  Returns the list of outgoing tradings on ascending order of trading amount.
     */
    public List<TradingInstruction> sortOutgoingTradingByAmount(final LocalDate settledDate) {
        return sort(settledDate, Direction.BUY);
    }

    private BigDecimal calculateTotal(LocalDate settledDate, Direction direction) {
        checkNotNull(settledDate, "Settled date should not be null");
        return tradingInstructions.stream()
                .filter(tradingInstruction -> tradingInstruction.getDirection() == direction)
                .filter(tradingInstruction -> tradingInstruction.getSettlementDate().isEqual(settledDate))
                .map(tradingInstruction -> tradingInstruction.determineTradeAmount())
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private List<TradingInstruction> sort(final LocalDate settledDate, final Direction direction) {
        checkNotNull(settledDate, "Settled date should not be null");
        return tradingInstructions.stream()
                .filter(tradingInstruction -> tradingInstruction.getDirection() == direction)
                .filter(tradingInstruction -> tradingInstruction.getSettlementDate().isEqual(settledDate))
                .sorted((ins1, ins2) -> ins1.determineTradeAmount().compareTo(ins2.determineTradeAmount()))
                .collect(Collectors.toList());
    }
}
