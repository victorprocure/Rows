package record;

/**
 * Created by vprocure on 7/11/2016.
 */
public class PerformanceRecord extends ActionRecord {
    @Override
    public void build(String[] columns) {
        RecordAction action = RecordAction.parseAction(columns[0]);
        if(action != RecordAction.PERF){
            throw new IllegalArgumentException("Not a performance record");
        }

        super.setAction(RecordAction.PERF);
        super.setEmployee(columns[1]);
        super.setRecordDate(columns[2]);
        super.setUnitsOrMultiplier(columns[3]);
    }

    @Override
    public void accept(IRecordVisitor visitor) {
        visitor.visit(this);
    }
}
