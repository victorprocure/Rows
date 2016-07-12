/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main;

import record.IRecordVisitor;
import record.MarketPriceRecord;
import record.RecordCalculatorVisitor;

import java.util.TreeMap;

/**
 * The Employee record management class is used as a data store of all unique employees and the record visitor assigned
 * to that user. This allows us to easily pull the visitor that is needed for our users and to guarantee that we keep
 * the user records unique.
 * Using the Java TreeMap as its default sort method for String is the same as requested by client.
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 */
class EmployeeRecordManagement {
    private final TreeMap<String, IRecordVisitor> employeeRecords;

    /**
     * Public constructor, this will initialize our TreeMap, removing the need for a setter
     */
    public EmployeeRecordManagement(){
        this.employeeRecords = new TreeMap<>();
    }

    /**
     * The has employee method is used to search our set for an employee.
     *
     * @param employee The name/Id of the employee we are looking for
     * @return Returns true if employee is found, false if not
     */
    private boolean hasEmployee(String employee) {
        return employeeRecords.containsKey(employee);
    }

    /**
     * The get visitor method is used to return the visitor currently assigned to the employee with a given Id.
     * If none is found we will add (put) a new record for an employee.
     *
     * @param employee The name of the employee
     * @return Returns the current visitor, or a new one if none found
     */
    public IRecordVisitor getVisitor(String employee){
        if (!hasEmployee(employee)) {
            // Create employee record as none has been created yet
            RecordCalculatorVisitor visitor = new RecordCalculatorVisitor();
            this.employeeRecords.put(employee, visitor);
            return visitor;
        }

        return this.employeeRecords.get(employee);

    }

    /**
     * As we handle market price records slightly different (they don't fall under our action record abstract) we use
     * this method to manually add them when we come across them in our input.
     *
     * @param record Record representing the current market value of an option/stock
     */
    public void addMarketRecord(MarketPriceRecord record){
        for(IRecordVisitor visitor : this.employeeRecords.values()){
            record.accept(visitor);
        }
    }

    /**
     * This method is used to output the employee option data. In CSV format to screen.
     * <p>
     * Not 100% I like this method being in our record management class, but it fits for now.
     */
    public void printEmployeeData() {
        for(String employee : this.employeeRecords.keySet()){
            IRecordVisitor visitor = this.employeeRecords.get(employee);
            System.out.println(employee + "," + visitor.output());
        }
    }
}
