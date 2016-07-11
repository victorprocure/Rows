package record;

/**
 * Created by vprocure on 7/11/2016.
 */
public interface IRecordVisitor {
    void visit(PerformanceRecord record);
    void visit(VestRecord record);
    void visit(SaleRecord record);
    void visit(MarketPriceRecord record);

    String output();
}
