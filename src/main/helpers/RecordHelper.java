/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main.helpers;

import main.record.MarketPriceRecord;
import main.record.PerformanceRecord;
import main.record.SaleRecord;
import main.record.VestRecord;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeSet;

/**
 * The Record Helper class, this is the class where I've put all the calculations and code to manipulate
 * the records in the visitor. I'm not 100% in love with the name of the class but it will do for now.
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 */
public final class RecordHelper {
    /**
     * The process sale method will calculate the total gain from the stocks that were sold.
     * In order to calculate this it will take the current market price available and subtract the original grant price
     * of the stock units. This will get the gain price, this is then used a multiplier to calculte total gain.
     * Rounding is done HALF_UP.
     *
     * @param vestRecord        This is the original main.record that represents the vested amount
     * @param marketPriceRecord This is the current market price
     * @return BigDecimal Returns the total gain calculated
     */
    public static BigDecimal processSale(VestRecord vestRecord, MarketPriceRecord marketPriceRecord) {
        BigDecimal gainPrice = calculateGain(marketPriceRecord.getMarketPrice(), vestRecord.getGrantPrice());

        BigDecimal unitsAsMultiplier = new BigDecimal(vestRecord.getUnitsOrMultiplier());

        return gainPrice.multiply(unitsAsMultiplier).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * This method will return the market price closest to the vested amount, this is a future use stub that I left in
     * while experimenting with some calculations. Works correctly for client request, but may need to be looked at for
     * future correctness.
     *
     * @param vestRecord This is the main.record of current vested stock, that we need to lookup market price for
     * @param marketPriceRecords This is the set of all market prices in our data
     * @return MarketPriceRecord Returns the closest Market Price to the date of the vested records
     */
    public static MarketPriceRecord getMarketPrice(VestRecord vestRecord, TreeSet<MarketPriceRecord> marketPriceRecords){
        for(MarketPriceRecord mr: marketPriceRecords.descendingSet()){
            if(mr.getMarketPriceDate().after(vestRecord.getRecordDate())){
                return mr;
            }
        }

        return null;
    }

    /**
     * The get recent sale method returns the sale closest to the date of a given vested stock option. This is used
     * to correctly calculate the sales and performance records later.
     *
     * @param vestRecord The vested option we are looking for a sale of
     * @param saleRecords The set of all sales
     * @return SaleRecord Will return a null if no sale found within date criteria
     */
    public static SaleRecord getRecentSale(VestRecord vestRecord, TreeSet<SaleRecord> saleRecords){
        for (SaleRecord sr : saleRecords.descendingSet()) {
            if (sr.getRecordDate().after(vestRecord.getRecordDate())) {
                return sr;
            }
        }

        return null;
    }

    /**
     * As an employee may have multiple performance records added we use this to find all the performance records that
     * have a date greater than the vested option date. This eliminates the possibility of having a performance bonus
     * applied to options that happen after the bonus is given.
     *
     * @param vestRecord This is the vested option we are looking for performance bonuses for
     * @param performanceRecords The set of all performance records
     * @return float Returns the multiplier to use against all vested units
     */
    public static float getPerformanceMultiplier(VestRecord vestRecord, TreeSet<PerformanceRecord> performanceRecords){
        float currentMultiplier = 1;

        for(PerformanceRecord pr : performanceRecords.descendingSet()){
            if(pr.getRecordDate().after(vestRecord.getRecordDate())) {
                currentMultiplier = getPerformanceMultiplier(pr, currentMultiplier);
            }
        }

        return  currentMultiplier;
    }

    /**
     * The get performance multiplier method will return the current mutliplier to use. Helping create the aggregate of
     * all multipliers.
     *
     * @param performanceRecord The performance main.record we are adding to our multiplier aggregate
     * @param currentMultiplier The current multiplier
     * @return float Returns the new multiplier aggregate
     */
    public static float getPerformanceMultiplier(PerformanceRecord performanceRecord, float currentMultiplier) {
        return performanceRecord.getUnitsOrMultiplier() * currentMultiplier;
    }

    /**
     * The get Vest Value method returns the total value of the vested option based on the given market price and any
     * performance multipliers
     *
     * @param vestRecord The vested option main.record we are running calculations against
     * @param marketPriceRecord The current market price main.record
     * @param multiplier The performance multiplier to use
     * @return BigDecimal Returns the vested option value, will never be negative 0.00 is the lowest
     */
    public static BigDecimal getVestValue(VestRecord vestRecord, MarketPriceRecord marketPriceRecord, float multiplier) {
        BigDecimal calculatedGainPrice = marketPriceRecord.getMarketPrice().subtract(vestRecord.getGrantPrice()).setScale(2, RoundingMode.HALF_UP);

        float units = multiplier * vestRecord.getUnitsOrMultiplier();

        BigDecimal totalVestedValue = calculatedGainPrice.multiply(new BigDecimal(units).setScale(2, RoundingMode.HALF_UP));

        // Make sure we do not allow total value to be less than 0
        if (totalVestedValue.intValue() < 0) {
            totalVestedValue = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
        }

        return calculatedGainPrice.multiply(new BigDecimal(units)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculate the gain price multiplier. This is done by subtracting the grant price from the current market value.
     * MarketPrice - GrantPrice = gainMultiplier
     *
     * @param marketPrice The current market price of option
     * @param grantPrice The grant price of the option
     * @return BigDecimal Returns the calculate gain multiplier
     */
    private static BigDecimal calculateGain(BigDecimal marketPrice, BigDecimal grantPrice) {
        return marketPrice.subtract(grantPrice).setScale(2, RoundingMode.HALF_UP);
    }
}
