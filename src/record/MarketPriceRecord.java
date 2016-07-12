/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package record;

import helpers.MethodHelpers;
import parser.DateParser;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Record represents the market price
 *
 * @author Victor Procur
 * @version 1.0
 * @since 2016-07-11
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

    public Date getMarketPriceDate() {
        return this.marketPriceDate;
    }

    private void setMarketPriceDate(String date){
        this.marketPriceDate = DateParser.convertStringToDate(date);
    }

    public BigDecimal getMarketPrice() {
        return this.marketPrice;
    }

    private void setMarketPrice(String price){
        MethodHelpers.checkParameterForNull(price, "Market price cannot be null");

        this.marketPrice = new BigDecimal(price);
    }

    public void accept(IRecordVisitor visitor){
        visitor.visit(this);
    }
}
