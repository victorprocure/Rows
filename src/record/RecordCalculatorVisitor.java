package record;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.TreeSet;

import helpers.RecordHelper;

/**
 * Created by vprocure on 7/11/2016.
 */
public class RecordCalculatorVisitor implements IRecordVisitor{
    private TreeSet<VestRecord> vestRecords;
    private TreeSet<PerformanceRecord> performanceRecords;
    private TreeSet<SaleRecord> saleRecords;
    private TreeSet<MarketPriceRecord> marketRecords;

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


    @Override
    public String output() {
        BigDecimal totalGainedFromSales = BigDecimal.ZERO;
        BigDecimal totalGained = BigDecimal.ZERO;

        for(VestRecord vr : this.vestRecords.descendingSet()){
            SaleRecord saleRecord = RecordHelper.getRecentSale(vr, this.saleRecords);
            MarketPriceRecord marketPriceRecord = RecordHelper.getMarketPrice(vr, this.marketRecords);

            if(marketPriceRecord == null){
                throw new IllegalArgumentException("Must have market price record");
            }

            if(this.saleRecords.iterator().hasNext()) {
                if (saleRecord != null) {
                    this.saleRecords.remove(saleRecord); // Make sure we don't process this sale again
                    vr.unitsOrMultiplier -= saleRecord.getUnitsOrMultiplier(); // remove those units, for future performance

                    totalGainedFromSales = totalGainedFromSales.add(RecordHelper.processSale(saleRecord, vr, marketPriceRecord));
                }
            }

            float multiplier = RecordHelper.getPerformanceMultiplier(vr, this.performanceRecords);
            totalGained = totalGained.add(RecordHelper.getVestValue(vr, marketPriceRecord, multiplier));
        }

        StringBuilder sb = new StringBuilder();
        sb.append(totalGained);
        sb.append(",");
        sb.append(totalGainedFromSales);

        return sb.toString();
    }
}
