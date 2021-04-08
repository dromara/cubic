package com.matrix.proxy.module;

import lombok.Data;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * 图表模型
 *
 * @Author qinqixuan
 * @Date 2021/04/06
 * @Version V1.0
 **/
@Data
public class ChartModel implements Serializable {

	private String name;

	private LinkedList<Object> xaxis;

	private LinkedList<Object> yaxis;

	private LinkedList<Object> tips;

	private Integer total;
}
