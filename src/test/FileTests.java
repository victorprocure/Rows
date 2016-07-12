/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package test;

import main.parser.FileParser;
import main.record.IRecordBuilder;
import main.record.VestRecord;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class FileTests {
    private FileParser parser;

    @BeforeTest
    public void initialize() {
        this.parser = new FileParser();
    }

    @Test
    public void lineWithoutMinimumColumnsAreIgnored() {
        String shortLine = "5";
        String validLine = "VEST,Victor,20160711,1000,0.45";

        IRecordBuilder builder = parser.getBuilderFromLine(shortLine);
        Assert.assertNull(builder);

        builder = parser.getBuilderFromLine(validLine);
        Assert.assertTrue(builder instanceof VestRecord);
    }

    @Test
    public void lineWithAndWithoutSpacesAreTreatedTheSame() {
        String validLineWithSpace = "VEST, Victor, 20160711, 1000, 0.45";
        String validLineWithoutSpace = "VEST,Victor,20160711,1000,0.45";

        IRecordBuilder spaceBuilder = parser.getBuilderFromLine(validLineWithSpace);
        Assert.assertNotNull(spaceBuilder);

        IRecordBuilder noSpaceBuilder = parser.getBuilderFromLine(validLineWithoutSpace);
        Assert.assertNotNull(noSpaceBuilder);

        noSpaceBuilder.build(validLineWithoutSpace.split(","));
        Assert.assertTrue(noSpaceBuilder instanceof VestRecord);

        spaceBuilder.build(validLineWithSpace.split(","));
        Assert.assertTrue(spaceBuilder instanceof VestRecord);

        VestRecord noSpaceRecord = (VestRecord) noSpaceBuilder;
        VestRecord spaceRecord = (VestRecord) spaceBuilder;

        Assert.assertEquals(noSpaceRecord.getAction(), spaceRecord.getAction());
        Assert.assertEquals(noSpaceRecord.getEmployee(), spaceRecord.getEmployee());
        Assert.assertEquals(noSpaceRecord.getRecordDate(), spaceRecord.getRecordDate());
    }
}
