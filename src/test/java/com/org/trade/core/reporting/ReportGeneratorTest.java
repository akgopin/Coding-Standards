package com.org.trade.core.reporting;

import com.org.trade.core.domain.EntityType;
import com.org.trade.core.domain.TradingInstruction;
import com.org.trade.core.domain.fixture.HappyPathBuilder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ReportGeneratorTest {

    private static final ReportGenerator REPORT_GENERATOR = ReportGenerator.of(HappyPathBuilder.buildTradingInstructions());

    @Rule
    public ExpectedException expectedEx = ExpectedException.none();

    @Test
    public void expect_reportGenerator_doesnot_build_when_instructions_are_null() {
        expectedEx.expect(NullPointerException.class);
        expectedEx.expectMessage("Trading instruction list should not be null");
        ReportGenerator.of(null);
    }

    @Test
    public void expect_reportGenerator_calculate_total_incoming_transaction() {
        BigDecimal total = REPORT_GENERATOR.calculateTotalOfIncomingTransactions(HappyPathBuilder.AUGUST_FOURTEEN_2017);
        assertThat(total, is(new BigDecimal("1306.00")));
    }

    @Test
    public void expect_zero_total_incoming_transaction_when_settled_date_doesnot_match_input_date() {
        BigDecimal total = REPORT_GENERATOR.calculateTotalOfIncomingTransactions(LocalDate.of(2017, 8, 13));
        assertThat(total, is(BigDecimal.ZERO));
    }

    @Test
    public void expect_zero_total_incoming_transaction_when_no_buy_transaction_present() {
        BigDecimal total = ReportGenerator.of(new ArrayList<>()).calculateTotalOfIncomingTransactions(HappyPathBuilder.AUGUST_FOURTEEN_2017);
        assertThat(total, is(BigDecimal.ZERO));
    }

    @Test
    public void expect_reportGenerator_calculate_total_outgoing_transaction() {
        BigDecimal total = REPORT_GENERATOR.calculateTotalOfOutgoingTransactions(HappyPathBuilder.AUGUST_FOURTEEN_2017);
        assertThat(total, is(new BigDecimal("1306.00")));
    }

    @Test
    public void expect_zero_total_outgoing_transaction_when_settled_date_doesnot_match_input_date() {
        BigDecimal total = REPORT_GENERATOR.calculateTotalOfOutgoingTransactions(LocalDate.of(2017, 8, 13));
        assertThat(total, is(BigDecimal.ZERO));
    }

    @Test
    public void expect_zero_total_outgoing_transaction_when_no_buy_transaction_present() {
        BigDecimal total = ReportGenerator.of(new ArrayList<>()).calculateTotalOfOutgoingTransactions(HappyPathBuilder.AUGUST_FOURTEEN_2017);
        assertThat(total, is(BigDecimal.ZERO));
    }

    @Test
    public void expect_incoming_transaction_to_be_sorted_in_ascending_order_of_amount() {
        List<TradingInstruction> incomingTradingTransactions = REPORT_GENERATOR.sortIncomingTradingByAmount(HappyPathBuilder.AUGUST_FOURTEEN_2017);
        List<EntityType> expectedOrder = Arrays.asList(EntityType.OIL, EntityType.CURRENCY, EntityType.GOLD);
        for (int i = 0; i < 3; i++) {
            assertThat(incomingTradingTransactions.get(i).getEntityType(), is(expectedOrder.get(i)));
        }
    }

    @Test
    public void expect_outgoing_transaction_to_be_sorted_in_ascending_order_of_amount() {
        List<TradingInstruction> incomingTradingTransactions = REPORT_GENERATOR.sortOutgoingTradingByAmount(HappyPathBuilder.AUGUST_FOURTEEN_2017);
        List<EntityType> expectedOrder = Arrays.asList(EntityType.OIL, EntityType.CURRENCY, EntityType.GOLD);
        for (int i = 0; i < 3; i++) {
            assertThat(incomingTradingTransactions.get(i).getEntityType(), is(expectedOrder.get(i)));
        }
    }

}
