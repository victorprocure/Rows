/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package test.mocks;

import record.PerformanceRecord;

public final class PerformanceRecordMock {
    public static PerformanceRecord getPerformanceRecordMock() {
        String line = "PERF,001B,20130102,1.5";

        PerformanceRecord record = new PerformanceRecord();
        record.build(line.split(","));

        return record;
    }
}
