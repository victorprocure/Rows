/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main.record;

import java.util.Comparator;

/**
 * This class is used to compare Market price records on the off chance our input gives us two
 */
class MarketPriceComparer implements Comparator<MarketPriceRecord> {
    /**
     * Compare the market price by date, with the latest taking priority
     *
     * @param o1
     * @param o2
     * @return
     */
    @Override
    public int compare(MarketPriceRecord o1, MarketPriceRecord o2) {
        if(o1.getMarketPriceDate().before(o2.getMarketPriceDate())) return 1;
        else return -1;
    }
}
