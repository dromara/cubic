package com.cubic.proxy.websocket;

import com.cubic.proxy.common.webserver.TextDetails;
import com.google.common.collect.ImmutableMap;

/**
 * @ClassName BaseCommandUtils
 * @Author QIANGLU
 * @Date 2020/4/26 2:19 下午
 * @Version 1.0
 */
public class BaseCommandUtils {

    public static final ImmutableMap<String,String> COMMAND_LIST  =  ImmutableMap.of("help", TextDetails.HELP_DETAILS,"search","");

}
