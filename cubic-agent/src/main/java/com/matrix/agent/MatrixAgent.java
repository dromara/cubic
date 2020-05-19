package com.matrix.agent;

import java.lang.instrument.Instrumentation;

/**
 * @ClassName MatrixAgent
 * @Author QIANGLU
 * @Date 2020/5/14 4:40 下午
 * @Version 1.0
 */

public class MatrixAgent {

    public static void premain(String agentArgs, Instrumentation instrumentation){
        System.out.println("add agent");

        instrumentation.addTransformer(new DefineTransformer(),true);
    }



}
