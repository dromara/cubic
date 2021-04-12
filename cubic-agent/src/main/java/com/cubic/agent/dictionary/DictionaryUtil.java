package com.cubic.agent.dictionary;

/**
 * @Author qinqixuan
 * @Date 2020/12/08
 * @Version V1.0
 **/
public class DictionaryUtil {

	public static int nullValue() {
		return 0;
	}

	public static boolean isNull(int id) {
		return id == nullValue();
	}

	public static int inexistence() {
		return -1;
	}
}
