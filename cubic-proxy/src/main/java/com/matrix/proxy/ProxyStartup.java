package com.matrix.proxy;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qianglu
 */
@SpringBootApplication
@MapperScan({"com.matrix.proxy.mapper","com.matrix.proxy.auth.mapper"})
public class ProxyStartup  {

	public static void main(String[] args) {

		SpringApplication.run(ProxyStartup.class, args);

	}


}
