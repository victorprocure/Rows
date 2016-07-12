/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main.record;

import java.util.Date;

/**
 * The IRecord interface is used for testing and in the building of our main.record classes, this is a representation of all
 * our action records.
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 */
public interface IRecord {
    /**
     * This method gets the date the main.record is valid for
     *
     * @return Date Returns the valid date of main.record
     */
    Date getRecordDate();

    /**
     * This method is used to return the employee associated with the main.record
     *
     * @return String Returns Employee name/Id
     */
    String getEmployee();

    /**
     * This method returns the units in the case of sales and vest records or multiplier in the case of performance records
     *
     * @return float Returns the units or multiplier associated with this action main.record
     */
    float getUnitsOrMultiplier();

    /**
     * This method will return the action type of the main.record
     *
     * @return RecordAction Returns the main.record action
     */
    RecordAction getAction();

    /**
     * This method is used to enforce our records ability to be visited by an implementatin of IRecordVisitor
     *
     * @param visitor The current tenant of our class
     */
    void accept(IRecordVisitor visitor);
}
