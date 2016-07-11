package record;

import java.math.BigDecimal;
import java.util.Date;

import helpers.MethodHelpers;
import parser.DateParser;

/**
 * Created by vprocure on 7/11/2016.
 */
public class MarketPriceRecord implements IRecordBuilder {
    private BigDecimal marketPrice;
    private Date marketPriceDate;

    @Override
    public void build(String[] columns){
        if(!DateParser.isValidDate(columns[0])){
            throw new IllegalArgumentException("Invalid Market Price Date");
        }

        this.setMarketPriceDate(columns[0]);
        this.setMarketPrice(columns[1]);
    }

    private void setMarketPriceDate(String date){
        this.marketPriceDate = DateParser.convertStringToDate(date);
    }

    private void setMarketPrice(String price){
        MethodHelpers.checkParameterForNull(price, "Market price cannot be null");

        this.marketPrice = new BigDecimal(price);
    }

    public Date getMarketPriceDate() {
        return this.marketPriceDate;
    }

    public BigDecimal getMarketPrice() {
        return this.marketPrice;
    }

    public void accept(IRecordVisitor visitor){
        visitor.visit(this);
    }
}
