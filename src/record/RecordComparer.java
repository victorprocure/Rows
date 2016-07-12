/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package record;

import java.util.Comparator;

public class RecordComparer implements Comparator<ActionRecord> {
    @Override
    public int compare(ActionRecord o1, ActionRecord o2) {
        if (o1.getRecordDate().before(o2.getRecordDate())) return -1;
        else return -1;
    }
}
