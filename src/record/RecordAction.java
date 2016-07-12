/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package record;

/**
 * Enum to represent the possible actions the input from our text file can have
 *
 * @author Victor Procure
 * @version 1.0
 * @since 2016-07-11
 */
public enum RecordAction {
    /**
     * Performance action
     */
    PERF,

    /**
     * Sale Action
     */
    SALE,

    /**
     * Vest stock option action
     */
    VEST;

    /**
     * Convert a string of an action to the enum value of the action
     * Originally this was throwing a parse error instead of returning null
     *
     * @param action The action we wish to convert to enum value
     * @return RecordAction Returns the enum value of an action or null.
     */
    public static RecordAction parseAction(String action) {
        for(RecordAction me : RecordAction.values()){
            if(action.equalsIgnoreCase(me.name())){
                return me;
            }
        }

        return null;
    }

    /**
     * Return the correct builder based on the action. In an idea world I'd try to decouple this more, but since the
     * enum is only 3 values. Not a huge deal.
     *
     * @param action The enum action that we wish to get a builder for
     * @return ActionRecord Returns the correct record builder
     */
    public static ActionRecord getBuilder(RecordAction action) {
        switch(action){
            case PERF:
                return new PerformanceRecord();
            case SALE:
                return new SaleRecord();
            case VEST:
                return new VestRecord();
        }

        return null;
    }
}
