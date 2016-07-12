/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package test.mocks;

import main.record.SaleRecord;

public final class SaleRecordMock {
    public static SaleRecord getSaleRecordMock() {
        String line = "SALE,001B,20120402,500,1.00";

        SaleRecord record = new SaleRecord();

        record.build(line.split(","));

        return record;
    }
}
