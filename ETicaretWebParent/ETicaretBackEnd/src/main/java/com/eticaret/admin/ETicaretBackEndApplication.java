package com.eticaret.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.eticaret.common.entity", "com.eticaret.admin.user"})
public class ETicaretBackEndApplication {
    public static void main(String[] args) {
        SpringApplication.run(ETicaretBackEndApplication.class, args);
    }

}
