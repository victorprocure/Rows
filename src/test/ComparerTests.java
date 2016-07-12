/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package test;

import org.testng.Assert;
import org.testng.annotations.Test;
import record.ActionRecord;
import record.RecordComparer;
import record.VestRecord;
import test.mocks.VestRecordMock;

import java.util.TreeSet;

public class ComparerTests {
    @Test
    public void secondRecordShouldAppearBeforeRecordOne() {
        VestRecord firstRecord = VestRecordMock.getVestRecordMock();
        VestRecord secondRecord = VestRecordMock.getSecondVestRecordMock();

        TreeSet<ActionRecord> records = new TreeSet<>(new RecordComparer());

        records.add(firstRecord);
        records.add(secondRecord);

        Assert.assertEquals(records.first(), secondRecord);
    }
}
