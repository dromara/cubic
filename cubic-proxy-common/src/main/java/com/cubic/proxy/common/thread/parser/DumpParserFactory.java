package com.cubic.proxy.common.thread.parser;

import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName DumpParserFactory
 * @Author QIANGLU
 * @Date 2022/9/6 22:45
 * @Version 1.0
 */
@Slf4j
public class DumpParserFactory {

    private static DumpParserFactory instance = null;

    /**
     * get the singleton instance of the factory
     *
     * @return singleton instance
     */
    public static DumpParserFactory get() {
        if (instance == null) {
            instance = new DumpParserFactory();
        }

        return (instance);
    }


    public void getDumpParser(String dumpFile) {

        try {
            SunJDKParser jdkParser = new SunJDKParser(dumpFile);
            jdkParser.parseDump();
        } catch (Exception e) {
            log.error("DumpParserFactory getDumpParser fail", e);
        }
    }
}
