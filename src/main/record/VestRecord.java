/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main.record;

import main.helpers.MethodHelpers;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class represents a vested option line
 *
 * @author Victor Procure
 * @see ActionRecord
 * @version 1.0
 * @since 2016-07-11
 */
public class VestRecord extends ActionRecord {
    private BigDecimal grantPrice;

    @Override
    public void build(String[] columns) {
        RecordAction action = RecordAction.parseAction(columns[0]);
        if(action != RecordAction.VEST){
            throw new IllegalArgumentException("Not a vesting main.record");
        }

        super.setAction(RecordAction.VEST);
        super.setEmployee(columns[1]);
        super.setRecordDate(columns[2]);
        super.setUnitsOrMultiplier(columns[3]);

        this.setGrantPrice(columns[4]);
    }

    public BigDecimal getGrantPrice() {
        return this.grantPrice;
    }

    private void setGrantPrice(String price){
        MethodHelpers.checkParameterForNull(price, "Grant price cannot be null");
        this.grantPrice = new BigDecimal(price.trim());
        this.grantPrice = this.grantPrice.setScale(2, RoundingMode.HALF_UP);
    }

    public void subtractUnits(float amount) {
        this.unitsOrMultiplier -= amount;
    }

    @Override
    public void accept(IRecordVisitor visitor) {
        visitor.visit(this);
    }
}
