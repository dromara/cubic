package com.cubic.agent.core;

import com.cubic.agent.core.boot.ServiceManager;
import com.cubic.agent.core.conf.CubicConfInitalizer;

import java.lang.instrument.Instrumentation;

public class MatrixStartup {

    public static void startup(Instrumentation instrumentation){
        CubicConfInitalizer.initConfig();
        ServiceManager.instrumentation = instrumentation;

        ServiceManager.INSTANCE.start();


        Runtime.getRuntime()
                .addShutdownHook(new Thread(ServiceManager.INSTANCE::shutdown, "cubic agent shutdown thread"));
    }

}
