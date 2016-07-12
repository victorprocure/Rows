/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package test.mocks;

import record.MarketPriceRecord;

public final class MarketPriceRecordMock {
    public static MarketPriceRecord getMockMarketPriceRecord() {
        String line = "20140101,1.00";

        MarketPriceRecord mpr = new MarketPriceRecord();
        mpr.build(line.split(","));

        return mpr;
    }
}
