package record;

import java.util.Comparator;

/**
 * Created by vprocure on 7/11/2016.
 */
public class MarketPriceComparer implements Comparator<MarketPriceRecord> {
    @Override
    public int compare(MarketPriceRecord o1, MarketPriceRecord o2) {
        if(o1.getMarketPriceDate().before(o2.getMarketPriceDate())) return 1;
        else return -1;
    }
}
