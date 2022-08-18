package com.matrix.proxy;


import cn.dev33.satoken.SaManager;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qianglu
 */
@Slf4j
@SpringBootApplication
@MapperScan({"com.matrix.proxy.mapper","com.matrix.proxy.auth.mapper"})
public class ProxyStartup  {

	public static void main(String[] args) {

		SpringApplication.run(ProxyStartup.class, args);
		log.info("启动成功：Sa-Token配置如下：" + SaManager.getConfig());

	}


}
