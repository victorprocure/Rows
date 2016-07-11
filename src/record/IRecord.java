package record;

import java.util.Date;

/**
 * Created by vprocure on 7/11/2016.
 */
public interface IRecord {
    Date getRecordDate();
    String getEmployee();
    float getUnitsOrMultiplier();
    RecordAction getAction();

    void accept(IRecordVisitor visitor);
}
