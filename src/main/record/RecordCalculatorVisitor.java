/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main.record;

import main.helpers.RecordHelper;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.TreeSet;

/**
 * The main.record visitor visits all main.record types and for the most part adds them to a set list for calculations once
 * all records have been visited per user
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 */
public class RecordCalculatorVisitor implements IRecordVisitor{
    private final TreeSet<VestRecord> vestRecords;
    private final TreeSet<PerformanceRecord> performanceRecords;
    private final TreeSet<SaleRecord> saleRecords;
    private final TreeSet<MarketPriceRecord> marketRecords;

    public RecordCalculatorVisitor() {
        this.vestRecords = new TreeSet<>(new RecordComparer());
        this.performanceRecords = new TreeSet<>(new RecordComparer());
        this.saleRecords = new TreeSet<>(new RecordComparer());
        this.marketRecords = new TreeSet<>(new MarketPriceComparer());
    }

    @Override
    public void visit(PerformanceRecord record) {
        this.performanceRecords.add(record);
    }

    @Override
    public void visit(VestRecord record) {
        this.vestRecords.add(record);
    }

    @Override
    public void visit(SaleRecord record) {
        this.saleRecords.add(record);
    }

    @Override
    public void visit(MarketPriceRecord record) {
        this.marketRecords.add(record);
    }


    /**
     * When all records have been visited this method is called. This allows us to make sure all records are processed
     * in the correct order, to not interfere with our calculations.
     * The order of operations for calculations is:
     * VestRecords > SaleRecords > PerfRecords
     * Sales have to be subtracted from vested options before performance calculations can be done
     * <p>
     * I could've trusted the input directly from the file which already has everything ordered correctly, however
     * this idea failed when I started at looking at ways to do parallization and chunking the input for quicker processing.
     * I did abandon these ideas in favor of a quick solution.
     * <p>
     * The best I could come up with was iterating through all vested option records, processing sales that came closest
     * to the main.record date of those vested options. Then removing that sale from the list of records, to ensure it didn't
     * get processed twice. Once that is done I calculate performance on the remaining vested options.
     * <p>
     * This method is a blatant violation of SRP, however for the sake of getting this solution done quickly due to time
     * constraints I opted for it. I'm also not a fan of my nested for loops to iterate through the various sets.
     * Can definitely use some work here.
     *
     * @return String Returns a comma delimited total gain and total sales gain amount
     */
    @Override
    public String output() {
        BigDecimal totalGainedFromSales = BigDecimal.ZERO;
        BigDecimal totalGained = BigDecimal.ZERO;

        for(VestRecord vr : this.vestRecords.descendingSet()){
            SaleRecord saleRecord = RecordHelper.getRecentSale(vr, this.saleRecords);
            MarketPriceRecord marketPriceRecord = RecordHelper.getMarketPrice(vr, this.marketRecords);

            if(marketPriceRecord == null){
                throw new IllegalArgumentException("Must have market price main.record");
            }

            if(this.saleRecords.iterator().hasNext()) {
                if (saleRecord != null) {
                    this.saleRecords.remove(saleRecord); // Make sure we don't process this sale again
                    vr.unitsOrMultiplier -= saleRecord.getUnitsOrMultiplier(); // remove those units, for future performance calculations if any

                    totalGainedFromSales = totalGainedFromSales.setScale(2, RoundingMode.HALF_UP).add(RecordHelper.processSale(vr, marketPriceRecord));
                }
            }

            float multiplier = RecordHelper.getPerformanceMultiplier(vr, this.performanceRecords);
            totalGained = totalGained.setScale(2, RoundingMode.HALF_UP).add(RecordHelper.getVestValue(vr, marketPriceRecord, multiplier));
        }

        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%.2f", totalGained));
        sb.append(",");
        sb.append(String.format("%.2f", totalGainedFromSales));

        return sb.toString();
    }
}
