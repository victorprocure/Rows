/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package parser;

import main.Globals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * The Date Parser class is used for any/all date methods that are needed by the application
 *
 * Credit to: http://stackoverflow.com/questions/11480542/fastest-way-to-tell-if-a-string-is-a-valid-date/
 * for a math based solution to find dates, reduces the overhead of using DateFormat
 * I opted to use a purely math based solution found on StackOverflow as it produced a quick benchmark, which I could
 * see being a benifit with large input files.
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 */
public final class DateParser {
    /**
     * Given a string the isValidDate method checks whether it is a date that is within range. For the cases of our
     * application the range is any year over 1900 with a month and date that make sense (I.E no 30 days in February)
     *
     * @param possibleDate The date we are aiming to check is valid
     * @return
     */
    public static boolean isValidDate(String possibleDate) {
        // Short circuit for blatant non-dates
        if (possibleDate == null || possibleDate.trim().length() != Globals.getDateFormat().length()) {
            return false;
        }


        int date;
        try {
            date = Integer.parseInt(possibleDate.trim());
        } catch (NumberFormatException e) {
            return false;
        }

        /* As our date is an integer of 8 bits in length ######## it has no decimals when math is processed against it
        * this means when we divide by 10000 we are removing any numbers that would be after the new decimal place
        * 20120103 instead of becoming 2012.0103 becomes 2012*/
        int year = date / 10000;

        /* Similar to above however in this case use the modulo operator, which for the date 20120102 gives us
         * 102 as the remainder, this divided by 100 gives us 1.02, as we do not have decimals leaves us with 1 as the month
         */
        int month = (date % 10000) / 100;

        /* Gives us the remainder of our date divided by 100 to seperate out the first two digits in the date */
        int day = date % 100;

        boolean yearOK = (year >= 1900); // making the assumption the lowest year we could conceive is 1900
        boolean monthOk = (month >= 1) && (month <= 12);
        boolean dayOk = (day >= 1) && (day <= daysInMonth(year, month));

        return (yearOK && monthOk && dayOk);
    }

    /**
     * This method converts our string to a Date data type. After first checking that it is a valid date
     *
     * @param possibleDate This is the date to convert
     * @return Date Returns the correct date or null if not valid
     */
    public static Date convertStringToDate(String possibleDate) {
        if (!DateParser.isValidDate(possibleDate)) {
            throw new IllegalArgumentException("Date must be valid");
        }

        DateFormat df = new SimpleDateFormat(Globals.getDateFormat());

        try {
            return df.parse(possibleDate.trim());
        } catch (ParseException ex) {
            // log exception
        }
        return null;
    }

    /**
     * This method is used to calculate the current days in a month, this method will not work for years prior to 1538
     * which should not be an issue for most.
     * For this the library Jodatime is recommended
     *
     * @param year  The year to calculate against
     * @param month The month we are checking
     * @return int Returns the amount of days in a given month and year
     */
    private static int daysInMonth(int year, int month) {
        int daysInMonth;

        /* Jan, March, May, July, August, October, December all have 31 days, short circuit for these months
        * February has a leap year every 4 years but not on centennials and every 400 years
        * All other months should have 30 days */
        switch (month) {
            case 1: // fall through
            case 3: // fall through
            case 5: // fall through
            case 7: // fall through
            case 8: // fall through
            case 10: // fall through
            case 12:
                daysInMonth = 31;
                break;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    daysInMonth = 29;
                } else {
                    daysInMonth = 28;
                }
                break;
            default:
                // returns 30 even for non-existant months
                daysInMonth = 30;
        }

        return daysInMonth;
    }
}
