package com.cubic.agent;


import com.cubic.agent.loader.MatrixAgentClassLoader;

import java.io.File;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.security.CodeSource;

/**
 * @ClassName MatrixAgent
 * @Author QIANGLU
 * @Date 2020/5/14 4:40 下午
 * @Version 1.0
 */

public class MatrixAgent {

    private static ClassLoader matrixClassLoader;

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("start to load matrix agent...");

        CodeSource codeSource = MatrixAgent.class.getProtectionDomain().getCodeSource();

        File matrixAgentJarFile = null;
        File matrixAgentCoreJarFile = null;
        if (codeSource != null) {
            System.out.println(String.format("matrix agent jar path:%s",codeSource.getLocation().getPath()));
            try {
                matrixAgentJarFile = new File(codeSource.getLocation().toURI().getSchemeSpecificPart());
                if(matrixAgentJarFile != null ){
                    String matrixAgentJarFilePath = codeSource.getLocation().getPath();
                    String matrixAgentCoreJarFilePath = matrixAgentJarFilePath.substring(0, matrixAgentJarFilePath.length() - "cubic-agent.jar".length()) + "cubic-agent-core.jar";
                    matrixAgentCoreJarFile = new File(matrixAgentCoreJarFilePath);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        if(matrixAgentJarFile == null || !matrixAgentJarFile.exists()){
            throw new RuntimeException("locate cubic-agent.jar path failed.");
        }

        if(matrixAgentCoreJarFile == null || !matrixAgentCoreJarFile.exists()){
            throw new RuntimeException("locate cubic-agent-core.jar path failed.");
        }

        //自定义classloader
        try{
            matrixClassLoader = new MatrixAgentClassLoader(new URL[]{matrixAgentCoreJarFile.toURI().toURL()});
        }catch (Throwable e){
            e.printStackTrace();
        }


        try{
            Class<?> bootstrapClass = matrixClassLoader.loadClass("com.cubic.agent.core.MatrixStartup");
            bootstrapClass.getMethod("startup", Instrumentation.class).invoke(null, instrumentation);
        }catch (Throwable ex){
            ex.printStackTrace();
        }

        System.out.println("finished loading matrix agent...");
    }

    public static ClassLoader getMatrixClassLoader(){
        return matrixClassLoader;
    }


}
