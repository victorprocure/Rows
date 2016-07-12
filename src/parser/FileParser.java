/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package parser;

import record.IRecordBuilder;
import record.MarketPriceRecord;
import record.RecordAction;

/**
 * The FileParser class is used to process all input from Standard Input of the given files
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 */
public class FileParser {
    /**
     * Return the correct record builder given the input line from standard input
     *
     * @param line The line we need to parse
     * @return IRecordBuilder Returns the builder to use to correctly build this line
     */
    public IRecordBuilder getBuilderFromLine(String line){
        boolean marketPriceLine = false;
        String[] columns = line.split(",");

        // short circuit any garbage rows
        if(columns.length <= 1){
            return null;
        }

        // Get the record action from our enum, or null if it is potentiall a market price row
        RecordAction recordAction = RecordAction.parseAction(columns[0]);

        // Check if this is the market price line, by seeing if the first column is a date
        if(recordAction == null && DateParser.isValidDate(columns[0]) && columns.length == 2){
            marketPriceLine = true;
        }

        IRecordBuilder builder;
        if(!marketPriceLine) {
            builder = RecordAction.getBuilder(recordAction);
        }else{
            builder = new MarketPriceRecord();
        }

        // Build out our new record for later use
        if (builder != null) {
            builder.build(columns);
        }

        return builder;
    }

}
