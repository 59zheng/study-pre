package org.example.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description
 * @author: ts
 * @create:2021-05-10 14:02
 */
@SpringBootApplication(scanBasePackages = {"org.example.shop", "org.example.rpc"})
public class ShopServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopServiceApplication.class, args);
    }

}
