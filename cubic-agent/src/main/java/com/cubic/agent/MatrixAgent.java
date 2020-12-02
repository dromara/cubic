package com.cubic.agent;


import com.cubic.agent.boot.ServiceManager;
import com.cubic.agent.conf.CubicConfInitalizer;

import java.lang.instrument.Instrumentation;

/**
 * @ClassName MatrixAgent
 * @Author QIANGLU
 * @Date 2020/5/14 4:40 下午
 * @Version 1.0
 */

public class MatrixAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        CubicConfInitalizer.initConfig();

        ServiceManager.INMSTANCE.start();


        Runtime.getRuntime()
                .addShutdownHook(new Thread(ServiceManager.INMSTANCE::shutdown, "cubic agent shutdown thread"));
    }


}
