package com.mos.bsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2 // 开启Swagger
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication(scanBasePackages = { "com.mos.bsd" })
@EnableScheduling
public class MosX3BsdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MosX3BsdApplication.class, args);
	}
}
