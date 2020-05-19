package com.matrix.cubic.agent.core.utils;

import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import com.sun.tools.attach.VirtualMachineDescriptor;

import java.io.IOException;
import java.util.List;

/**
 * @ClassName VmUtils
 * @Author QIANGLU
 * @Date 2020/3/30 4:37 下午
 * @Version 1.0
 */
public class VmUtils {

    public static VirtualMachine getVirtualMachine(String pid) throws IOException, AttachNotSupportedException {

        VirtualMachineDescriptor targetVM = null;

        List<VirtualMachineDescriptor> virtualMachineDescriptors = VirtualMachine.list();
        for (VirtualMachineDescriptor descriptor : virtualMachineDescriptors) {
            if (descriptor.id().equals(pid)) {
                targetVM = descriptor;
                break;
            }
        }
        if (targetVM == null) {
            throw new IllegalArgumentException("could not find the target jvm by process id:" + pid);
        }

        return VirtualMachine.attach(pid);
    }

}
