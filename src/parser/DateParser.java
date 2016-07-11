package parser;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

import main.Globals;

/**
 * Created by vprocure on 7/11/2016.
 *
 * Credit to: http://stackoverflow.com/questions/11480542/fastest-way-to-tell-if-a-string-is-a-valid-date/
 * for a math based solution to find dates, reduces the overhead of using DateFormat
 */
public final class DateParser {
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

        int year = date / 10000;
        int month = (date % 10000) / 100;
        int day = date % 100;

        boolean yearOK = (year >= 1900); // making the assumption the lowest year we could conceive is 1900
        boolean monthOk = (month >= 1) && (month <= 12);
        boolean dayOk = (day >= 1) && (day <= daysInMonth(year, month));

        return (yearOK && monthOk && dayOk);
    }

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

    private static int daysInMonth(int year, int month) {
        int daysInMonth;
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
