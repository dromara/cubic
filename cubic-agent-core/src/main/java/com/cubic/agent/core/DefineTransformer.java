package com.cubic.agent.core;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @ClassName DefineTransformer
 * @Author QIANGLU
 * @Date 2020/5/15 10:00 上午
 * @Version 1.0
 */
public class DefineTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        System.out.println("premain load class: "+className);
        return classfileBuffer;
    }
}
