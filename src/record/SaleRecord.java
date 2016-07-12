/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package record;

import helpers.MethodHelpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class represents a vested option sale line
 *
 * @author Victor Procure
 * @see ActionRecord
 * @version 1.0
 * @since 2016-07-11
 */
public class SaleRecord extends ActionRecord {
    private BigDecimal salePrice;

    @Override
    public void build(String[] columns) {
        RecordAction action = RecordAction.parseAction(columns[0]);
        if(action != RecordAction.SALE){
            throw new IllegalArgumentException("Not a sales record");
        }

        super.setAction(RecordAction.SALE);
        super.setEmployee(columns[1]);
        super.setRecordDate(columns[2]);
        super.setUnitsOrMultiplier(columns[3]);

        this.setSalePrice(columns[4]);
    }

    private void setSalePrice(String price){
        MethodHelpers.checkParameterForNull(price, "Sale price cannot be null");
        this.salePrice = new BigDecimal(price.trim()).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void accept(IRecordVisitor visitor) {
        visitor.visit(this);
    }
}
