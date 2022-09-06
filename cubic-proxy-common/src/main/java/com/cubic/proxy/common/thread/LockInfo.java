package com.cubic.proxy.common.thread;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LockInfo {

    private String id;
    private String tid;
    private String nid;
    private String state;
    private int owned;
}
