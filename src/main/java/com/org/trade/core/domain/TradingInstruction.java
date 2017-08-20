package com.org.trade.core.domain;

import com.google.common.base.Objects;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TradingInstruction {

    private final ProductType productType;
    private final Direction direction;
    private final BigDecimal agreedFxRate;
    private final String currency;
    private final LocalDate instructionDate;
    private final LocalDate settlementDate;
    private final int noOfUnits;
    private final BigDecimal pricePerUnit;

    private TradingInstruction(final Builder builder) {
        productType = checkNotNull(builder.productType, "Product type should not be null when building %s", this.getClass());
        direction = checkNotNull(builder.direction, "Direction should not be null when building %s", this.getClass());
        agreedFxRate = checkNotNull(builder.agreedFxRate, "Agreed FX Rate should not be null when building %s", this.getClass());
        checkState(agreedFxRate.compareTo(BigDecimal.ZERO)>0,"Agreed rate should be greater than zero");
        currency = checkNotNull(builder.currency, "Currency should not be null when building %s", this.getClass());
        instructionDate = checkNotNull(builder.instructionDate, "Instruction date should not be null when building %s", this.getClass());
        settlementDate = checkNotNull(builder.settlementDate, "Settlement date should not be null when building %s", this.getClass());
        checkState(builder.noOfUnits > 0,"No of units should be greater than zero");
        noOfUnits = builder.noOfUnits;
        pricePerUnit = checkNotNull(builder.pricePerUnit, "Price per unit should not be null when building %s", this.getClass());
        checkState(pricePerUnit.compareTo(BigDecimal.ZERO)>0,"Price per unit should be greater than zero");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o){ return true; }
        if (o == null || getClass() != o.getClass()){ return false;}
        TradingInstruction that = (TradingInstruction) o;
        return noOfUnits == that.noOfUnits &&
                productType == that.productType &&
                direction == that.direction &&
                Objects.equal(agreedFxRate, that.agreedFxRate) &&
                Objects.equal(currency, that.currency) &&
                Objects.equal(instructionDate, that.instructionDate) &&
                Objects.equal(settlementDate, that.settlementDate) &&
                Objects.equal(pricePerUnit, that.pricePerUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(productType, direction, agreedFxRate, currency, instructionDate, settlementDate, noOfUnits, pricePerUnit);
    }


    public static class Builder {

        private ProductType productType;
        private Direction direction;
        private BigDecimal agreedFxRate;
        private String currency;
        private LocalDate instructionDate;
        private LocalDate settlementDate;
        private int noOfUnits;
        private BigDecimal pricePerUnit;

        private Builder() {
        }

        public static Builder newBuilder() {
            return new Builder();
        }

        public Builder withProductType(final ProductType productType) {
            this.productType = productType;
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

        public Builder withNoOfUnits(final int noOfUnits) {
            this.noOfUnits = noOfUnits;
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
