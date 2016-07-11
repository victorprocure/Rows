package parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import record.ActionRecord;
import record.IRecordBuilder;
import record.MarketPriceRecord;
import record.RecordAction;

/**
 * Created by vprocure on 7/11/2016.
 */
public class FileParser {
    public IRecordBuilder getBuilderFromLine(String line){
        boolean marketPriceLine = false;
        String[] columns = line.split(",");

        if(columns.length <= 1){
            return null;
        }

        RecordAction recordAction = RecordAction.parseAction(columns[0]);

        // Check if this is the market price line
        if(recordAction == null && DateParser.isValidDate(columns[0]) && columns.length == 2){
            marketPriceLine = true;
        }

        IRecordBuilder builder;
        if(!marketPriceLine) {
            builder = RecordAction.getBuilder(recordAction);
        }else{
            builder = new MarketPriceRecord();
        }

        builder.build(columns);

        return builder;
    }

}
