/*
 * Copyright (c) 2016. Unless otherwise stated all code developed by Victor Procure
 */

package main.record;

/**
 * Interface for all records that need to be built from input lines
 */
public interface IRecordBuilder {
    /**
     * This method allows the implementation to be built from the standard input line given to it
     *
     * @param columns The columns of the lines, originally comma delimited
     */
    void build(String[] columns);
}
