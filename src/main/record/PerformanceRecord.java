/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main.record;

/**
 * This class represents a performance multiplier line
 *
 * @author Victor Procure
 * @see ActionRecord
 * @version 1.0
 * @since 2016-07-11
 */
public class PerformanceRecord extends ActionRecord {
    @Override
    public void build(String[] columns) {
        RecordAction action = RecordAction.parseAction(columns[0]);
        if(action != RecordAction.PERF){
            throw new IllegalArgumentException("Not a performance main.record");
        }

        super.setAction(RecordAction.PERF);
        super.setEmployee(columns[1]);
        super.setRecordDate(columns[2]);
        super.setUnitsOrMultiplier(columns[3]);
    }

    @Override
    public void accept(IRecordVisitor visitor) {
        visitor.visit(this);
    }
}
