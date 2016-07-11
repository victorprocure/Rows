package record;

/**
 * Created by vprocure on 7/11/2016.
 */
public enum RecordAction {
    PERF,
    SALE,
    VEST;

    public static final RecordAction parseAction(String action){
        for(RecordAction me : RecordAction.values()){
            if(action.equalsIgnoreCase(me.name())){
                return me;
            }
        }

        return null;
    }

    public static final ActionRecord getBuilder(RecordAction action){
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
