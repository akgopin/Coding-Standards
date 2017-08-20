package com.org.trade.core.domain;

import com.google.common.base.Objects;
import com.org.trade.core.domain.util.DateUtil;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/*
*  Trading instruction is a domain object that represents an instruction customer has requested the trading
 *  organisation to execute in the international market.
*
*/
public class TradingInstruction {

    /*
    *  Entity to be traded see {@link EntityType}
    */
    private final EntityType entityType;
    /*
     * Indicates if it  is a buy or sell instruction {@link Direction}
     */
    private final Direction direction;
    /*
    * Agreed rate at which the entity to be bought or sold in the market.
    * Rate is the USD -> Currency exchange rate
    */
    private final BigDecimal rate;
    /*
    * Currency in which trade to be executed.
    */
    private final String currency;
    /*
    * Date on which the customers send the instruction
    */
    private final LocalDate instructionDate;
    /*
    * Date on which the instruction to be settled
    */
    private final LocalDate settlementDate;
    /*
    * Number of units to be sold or bought
    */
    private final int numberOfUnits;
    /*
    * Price per unit
    * */
    private final BigDecimal pricePerUnit;

    private TradingInstruction(final Builder builder) {
        entityType = checkNotNull(builder.entityType, "Entity type should not be null when building %s", this.getClass());
        direction = checkNotNull(builder.direction, "Direction should not be null when building %s", this.getClass());
        rate = checkNotNull(builder.agreedFxRate, "Agreed FX Rate should not be null when building %s", this.getClass());
        checkState(rate.compareTo(BigDecimal.ZERO) > 0, "Agreed rate should be greater than zero");
        currency = checkNotNull(builder.currency, "Currency should not be null when building %s", this.getClass());
        instructionDate = checkNotNull(builder.instructionDate, "Instruction date should not be null when building %s", this.getClass());
        checkNotNull(builder.settlementDate, "Settlement date should not be null when building %s", this.getClass());
        settlementDate = determineSettlementDate(builder.settlementDate);
        checkState(builder.numberOfUnits > 0, "No of units should be greater than zero");
        numberOfUnits = builder.numberOfUnits;
        pricePerUnit = checkNotNull(builder.pricePerUnit, "Price per unit should not be null when building %s", this.getClass());
        checkState(pricePerUnit.compareTo(BigDecimal.ZERO) > 0, "Price per unit should be greater than zero");
    }

    /*
    *  Calculates the USD equivalent amount of the trade.
    *
    * */
    public BigDecimal determineTradeAmount() {
        return pricePerUnit.multiply(rate).multiply(new BigDecimal(numberOfUnits));
    }

    private LocalDate determineSettlementDate(final LocalDate settlementDate) {
        if (isCurrencyAEDorSAR()) {
            return DateUtil.determineNextAllowableDate(settlementDate, Weekend.FRI_SAT);
        } else {
            return DateUtil.determineNextAllowableDate(settlementDate, Weekend.SAT_SUN);
        }
    }

    private boolean isCurrencyAEDorSAR() {
        return "AED".equalsIgnoreCase(currency) || "SAR".equalsIgnoreCase(currency);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TradingInstruction that = (TradingInstruction) o;
        return numberOfUnits == that.numberOfUnits &&
                entityType == that.entityType &&
                direction == that.direction &&
                Objects.equal(rate, that.rate) &&
                Objects.equal(currency, that.currency) &&
                Objects.equal(instructionDate, that.instructionDate) &&
                Objects.equal(settlementDate, that.settlementDate) &&
                Objects.equal(pricePerUnit, that.pricePerUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(entityType, direction, rate, currency, instructionDate, settlementDate, numberOfUnits, pricePerUnit);
    }


    public static class Builder {

        private EntityType entityType;
        private Direction direction;
        private BigDecimal agreedFxRate;
        private String currency;
        private LocalDate instructionDate;
        private LocalDate settlementDate;
        private int numberOfUnits;
        private BigDecimal pricePerUnit;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withEntityType(final EntityType entityType) {
            this.entityType = entityType;
            return this;
        }

        public Builder withDirection(final Direction direction) {
            this.direction = direction;
            return this;
        }

        public Builder withAgreedFxRate(final BigDecimal agreedFxRate) {
            this.agreedFxRate = agreedFxRate;
            return this;
        }

        public Builder withCurrency(final String currency) {
            this.currency = currency;
            return this;
        }

        public Builder withInstructionDate(final LocalDate instructionDate) {
            this.instructionDate = instructionDate;
            return this;
        }

        public Builder withSettlementDate(final LocalDate settlementDate) {
            this.settlementDate = settlementDate;
            return this;
        }

        public Builder withNumberOfUnits(final int numberOfUnits) {
            this.numberOfUnits = numberOfUnits;
            return this;
        }

        public Builder withPricePerUnit(final BigDecimal pricePerUnit) {
            this.pricePerUnit = pricePerUnit;
            return this;
        }

        public TradingInstruction build() {
            return new TradingInstruction(this);
        }
    }
}
