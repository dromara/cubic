
package com.cubic.agent.utils;

import java.lang.management.ManagementFactory;

public class ProcessorUtil {

    public static int getNumberOfProcessors() {
        return ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors();
    }
}
