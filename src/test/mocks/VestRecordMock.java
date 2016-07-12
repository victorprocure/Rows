/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package test.mocks;

import record.VestRecord;

public final class VestRecordMock {
    public static VestRecord getVestRecordMock() {
        String line = "VEST,001B,20120102,1000,0.45";

        VestRecord record = new VestRecord();
        record.build(line.split(","));

        return record;
    }

    public static VestRecord getSecondVestRecordMock() {
        String line = "VEST,002B,20120101,1500,0.45";

        VestRecord record = new VestRecord();
        record.build(line.split(","));

        return record;
    }
}
