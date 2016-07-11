package main;

import java.util.Iterator;
import java.util.TreeMap;
import java.util.TreeSet;

import record.ActionRecord;
import record.IRecordVisitor;
import record.MarketPriceRecord;
import record.RecordCalculatorVisitor;

/**
 * Created by vprocure on 7/11/2016.
 */
public class EmployeeRecordManagement {
    private TreeMap<String, IRecordVisitor> employeeRecords;

    public EmployeeRecordManagement(){
        this.employeeRecords = new TreeMap<>();
    }

    public TreeMap<String, IRecordVisitor> getEmployeeRecords(){
        return this.employeeRecords;
    }

    public boolean hasEmployee(String employee){
        return employeeRecords.containsKey(employee);
    }

    public IRecordVisitor getVisitor(String employee){
        if(!hasEmployee(employee)){
            RecordCalculatorVisitor visitor = new RecordCalculatorVisitor();
            this.employeeRecords.put(employee, visitor);
            return visitor;
        }

        return this.employeeRecords.get(employee);

    }

    public void addMarketRecord(MarketPriceRecord record){
        for(IRecordVisitor visitor : this.employeeRecords.values()){
            record.accept(visitor);
        }
    }

    public void printEmployeeData() {
        for(String employee : this.employeeRecords.keySet()){
            IRecordVisitor visitor = this.employeeRecords.get(employee);
            System.out.println(employee + "," + visitor.output());
        }
    }
}
