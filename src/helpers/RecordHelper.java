package helpers;

import java.math.BigDecimal;
import java.util.TreeSet;

import record.MarketPriceRecord;
import record.PerformanceRecord;
import record.SaleRecord;
import record.VestRecord;

/**
 * Created by vprocure on 7/11/2016.
 */
public final class RecordHelper {
    public static BigDecimal processSale(SaleRecord saleRecord, VestRecord vestRecord, MarketPriceRecord marketPriceRecord){
        BigDecimal gainPrice = calculateGain(marketPriceRecord.getMarketPrice(), vestRecord.getGrantPrice());

        return gainPrice.multiply(new BigDecimal(vestRecord.getUnitsOrMultiplier()));
    }

    public static MarketPriceRecord getMarketPrice(VestRecord vestRecord, TreeSet<MarketPriceRecord> marketPriceRecords){
        for(MarketPriceRecord mr: marketPriceRecords.descendingSet()){
            if(mr.getMarketPriceDate().after(vestRecord.getRecordDate())){
                return mr;
            }
        }

        return null;
    }

    public static SaleRecord getRecentSale(VestRecord vestRecord, TreeSet<SaleRecord> saleRecords){
        for (SaleRecord sr : saleRecords.descendingSet()) {
            if (sr.getRecordDate().after(vestRecord.getRecordDate())) {
                return sr;
            }
        }

        return null;
    }

    public static float getPerformanceMultiplier(VestRecord vestRecord, TreeSet<PerformanceRecord> performanceRecords){
        float currentMultiplier = 1;

        for(PerformanceRecord pr : performanceRecords.descendingSet()){
            if(pr.getRecordDate().after(vestRecord.getRecordDate())){
                currentMultiplier *= pr.getUnitsOrMultiplier();
            }
        }

        return  currentMultiplier;
    }

    public static BigDecimal getVestValue(VestRecord vestRecord, MarketPriceRecord marketPriceRecord, float multiplier){
        BigDecimal calculatedGainPrice = marketPriceRecord.getMarketPrice().subtract(vestRecord.getGrantPrice());

        float units = multiplier * vestRecord.getUnitsOrMultiplier();

        return calculatedGainPrice.multiply(new BigDecimal(units));
    }

    private static BigDecimal calculateGain(BigDecimal marketPrice, BigDecimal grantPrice){
        return marketPrice.subtract(grantPrice);
    }
}
