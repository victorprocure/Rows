/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package record;

/**
 * Common implementation of the visitor pattern, needed interface
 * A visitor for each type of record
 */
public interface IRecordVisitor {
    void visit(PerformanceRecord record);
    void visit(VestRecord record);
    void visit(SaleRecord record);
    void visit(MarketPriceRecord record);

    /**
     * This method outputs the visitor implementation, was used for testing and is now used for CSV output
     * Should've probably refactored this elsewhere, but kept here for now.
     *
     * @return
     */
    String output();
}
