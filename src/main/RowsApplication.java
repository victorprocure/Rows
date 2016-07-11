package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import parser.FileParser;
import record.ActionRecord;
import record.IRecordBuilder;
import record.IRecordVisitor;
import record.MarketPriceRecord;

/**
 * Created by vprocure on 7/11/2016.
 */
public class RowsApplication {
    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        FileParser parser = new FileParser();
        EmployeeRecordManagement employeeData = new EmployeeRecordManagement();


        try {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                if (line.length() == 0) {
                    break;
                }

                IRecordBuilder builder = parser.getBuilderFromLine(line);

                if (builder instanceof ActionRecord) {
                    ActionRecord record = (ActionRecord) builder;

                    IRecordVisitor visitor = employeeData.getVisitor(record.getEmployee());
                    record.accept(visitor);
                } else if (builder instanceof MarketPriceRecord) {
                    MarketPriceRecord record = (MarketPriceRecord) builder;
                    employeeData.addMarketRecord(record);
                }

            }
        } catch (IOException ex) {
            //log
        }

        employeeData.printEmployeeData();
    }
}
