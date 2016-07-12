/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package test;

import main.Globals;
import org.testng.Assert;
import org.testng.annotations.Test;
import parser.DateParser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTests {
    @Test
    public void verifyDateHasCorrectValues() {
        String validDate = "19860725";
        String invalidDate = "19860230";

        Assert.assertTrue(DateParser.isValidDate(validDate));
        Assert.assertFalse(DateParser.isValidDate(invalidDate));
    }

    @Test
    public void verifyStringCorrectlyConvertedToDate() throws ParseException {
        String validDate = "19860725";
        DateFormat df = new SimpleDateFormat(Globals.getDateFormat());

        Date valid = df.parse(validDate);

        Assert.assertEquals(DateParser.convertStringToDate(validDate), valid);
    }
}
