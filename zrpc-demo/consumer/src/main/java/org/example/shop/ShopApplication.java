package org.example.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author yanzheng
 * @Date 2022/12/28 16:31
 */
@SpringBootApplication(scanBasePackages = {"org.example.shop", "org.example.rpc"})
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class, args);
    }
}
