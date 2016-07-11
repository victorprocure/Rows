package record;

import java.util.Comparator;

/**
 * Created by vprocure on 7/11/2016.
 */
public class RecordComparer implements Comparator<ActionRecord> {
    @Override
    public int compare(ActionRecord o1, ActionRecord o2) {
        if(o1.getRecordDate().before(o2.getRecordDate())) return 1;
        if(o2.getRecordDate().after(o2.getRecordDate())) return -1;

        return 0;
    }
}
