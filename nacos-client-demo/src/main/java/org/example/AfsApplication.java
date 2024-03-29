package org.example;

import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@NacosPropertySource(dataId = "afs-test", autoRefreshed = true)
public class AfsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AfsApplication.class, args);
	}
}
