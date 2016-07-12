/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package record;

import helpers.MethodHelpers;
import parser.DateParser;

import java.util.Date;

/**
 * This is an abstraction of all our action records
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 * @see IRecord
 */
public abstract class ActionRecord implements IRecord, IRecordBuilder {
    float unitsOrMultiplier;
    private Date recordDate;
    private String employee;
    private RecordAction action;

    @Override
    public Date getRecordDate() {
        return this.recordDate;
    }

    void setRecordDate(String date) {
        this.recordDate = DateParser.convertStringToDate(date);
    }

    @Override
    public String getEmployee() {
        return this.employee;
    }

    void setEmployee(String employee) {
        MethodHelpers.checkParameterForNull(employee,
                "Employee cannot be empty");

        this.employee = employee.trim();
    }

    @Override
    public float getUnitsOrMultiplier() {
        return this.unitsOrMultiplier;
    }

    void setUnitsOrMultiplier(String unitsOrMultiplier) {
        MethodHelpers.checkParameterForNull(unitsOrMultiplier,
                "Units or multiplier cannot be empty");

        this.unitsOrMultiplier = Float.parseFloat(unitsOrMultiplier.trim());
    }

    @Override
    public RecordAction getAction() {
        return this.action;
    }

    void setAction(RecordAction action) {
        this.action = action;
    }
}
