package record;

import java.util.Date;

import helpers.MethodHelpers;
import parser.DateParser;

/**
 * Created by vprocure on 7/11/2016.
 */
public abstract class ActionRecord implements IRecord, IRecordBuilder {
    Date recordDate;
    String employee;
    float unitsOrMultiplier;
    RecordAction action;

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
