/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main.helpers;

/**
 * The method main.helpers class is a collection of all main.helpers I've found useful and in order to not repeat code have
 * refactored out into their own package.
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 */
public final class MethodHelpers {
    /**
     * This method is used to check method parameters for null values. One line as opposed to multiple if statements
     * littering code.
     *
     * @param param   This is the parameter that will be checked for null value
     * @param message This is the message that will be thrown if null found
     */
    public static void checkParameterForNull(Object param, String message){
        if(param == null){
            throw new IllegalArgumentException(message);
        }
    }
}
