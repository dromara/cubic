package com.cubic.proxy.common.thread;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ThreadInfo {
    private String name;
    private String tid;
    private String nid;
    private String state;
    private String rawData;
}
