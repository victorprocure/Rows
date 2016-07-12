/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main;

@SuppressWarnings("ALL")
/**
 * The Globals class is used to store any global variables for our application. In an ideal world I would be using
 * application resources for this. But this works for now as there is only one needed global so far.
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 */
public final class Globals {
    /**
     * This method is used to return the date format used in our input, for all date conversions
     *
     * @return String Returns the date format
     */
    public static String getDateFormat(){
        return "yyyymmdd";
    }
}
