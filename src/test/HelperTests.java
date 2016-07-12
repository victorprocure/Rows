/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package test;

import main.helpers.MethodHelpers;
import main.helpers.RecordHelper;
import main.record.MarketPriceRecord;
import main.record.PerformanceRecord;
import main.record.SaleRecord;
import main.record.VestRecord;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import test.mocks.MarketPriceRecordMock;
import test.mocks.PerformanceRecordMock;
import test.mocks.SaleRecordMock;
import test.mocks.VestRecordMock;

import java.math.BigDecimal;
import java.math.RoundingMode;

@SuppressWarnings("ALL")
public class HelperTests {
    private MarketPriceRecord marketPriceRecordMock;
    private VestRecord vestRecordMock;
    private SaleRecord saleRecordMock;
    private PerformanceRecord performanceRecordMock;

    @BeforeTest
    public void initialize() {
        this.marketPriceRecordMock = MarketPriceRecordMock.getMockMarketPriceRecord();
        this.vestRecordMock = VestRecordMock.getVestRecordMock();
        this.saleRecordMock = SaleRecordMock.getSaleRecordMock();
        this.performanceRecordMock = PerformanceRecordMock.getPerformanceRecordMock();
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void anExceptionWillBeThrownIfArgumentNull() {
        String arg = null;

        MethodHelpers.checkParameterForNull(arg, "Test, this was needed to be thrown");
    }

    @Test
    public void saleProcessingReturnsCorrectValue() {
        BigDecimal expectedGainedFromSale = new BigDecimal(275).setScale(2, RoundingMode.HALF_UP);
        BigDecimal expectedGained = new BigDecimal(412.50).setScale(2, RoundingMode.HALF_UP);

        this.vestRecordMock.subtractUnits(this.saleRecordMock.getUnitsOrMultiplier());

        BigDecimal totalGainThroughSale = RecordHelper.processSale(this.vestRecordMock, this.marketPriceRecordMock);
        Assert.assertEquals(totalGainThroughSale, expectedGainedFromSale);

        float multiplier = RecordHelper.getPerformanceMultiplier(this.performanceRecordMock, 1);
        BigDecimal totalGained = RecordHelper.getVestValue(this.vestRecordMock, this.marketPriceRecordMock, multiplier);

        Assert.assertEquals(totalGained, expectedGained);
    }

    @Test
    public void performanceProcessingReturnsCorrectValue() {
        BigDecimal expected = new BigDecimal(825.00).setScale(2, RoundingMode.HALF_UP);

        float multiplier = RecordHelper.getPerformanceMultiplier(this.performanceRecordMock, 1);
        BigDecimal totalGained = RecordHelper.getVestValue(this.vestRecordMock, this.marketPriceRecordMock, multiplier);

        Assert.assertEquals(totalGained, expected);
    }
}
