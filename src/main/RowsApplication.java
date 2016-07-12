/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main;

import main.parser.FileParser;
import main.record.ActionRecord;
import main.record.IRecordBuilder;
import main.record.IRecordVisitor;
import main.record.MarketPriceRecord;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * This is the entry point class to our application and gets the party started
 */
class RowsApplication {
    /**
     * This is the main method/application entry point
     *
     * @param args Any command line arguments that were given by the user
     */
    public static void main(String[] args) {
        /* using BufferedReader as it seems to have a slight performance lead over Scanner from the research I found
            done by others
         */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        FileParser parser = new FileParser();

        EmployeeRecordManagement employeeData = new EmployeeRecordManagement();


        try {
            for (String line = br.readLine(); line != null; line = br.readLine()) {
                /* This if statement is not needed for the compiled version, however for the debugger the InputStream was using
                * TCP which was causing the stream to remain open regardless of null inputs. Leaving it in for historical */
                if (line.length() == 0) {
                    break;
                }

                // Build our builder from the text line
                IRecordBuilder builder = parser.getBuilderFromLine(line);

                /* As our Market Price records are different that other records (no user information) we use reflection
                * to find out if our builder is a market price, if its not most likely an ActionRecord
                * that "Most likely" is why I put the null check */
                if (builder instanceof MarketPriceRecord) {
                    MarketPriceRecord record = (MarketPriceRecord) builder;
                    employeeData.addMarketRecord(record);
                } else if (builder != null) {
                    ActionRecord record = (ActionRecord) builder;

                    IRecordVisitor visitor = employeeData.getVisitor(record.getEmployee());

                    record.accept(visitor);
                }

            }
        } catch (IOException ex) {
            // This is where any logging would go
        }

        employeeData.printEmployeeData();
    }
}
