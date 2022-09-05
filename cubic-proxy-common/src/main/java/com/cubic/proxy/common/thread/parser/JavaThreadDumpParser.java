package com.cubic.proxy.common.thread.parser;

public interface JavaThreadDumpParser {
    /**
     *
     * @param fullpathFileName - dump file name
     */

    public void parseJavaThreadDumpFile(String fullpathFileName);
}
