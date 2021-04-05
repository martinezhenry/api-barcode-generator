package com.hvscode.api.barcodegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BarCodeApplication {

    public static void main(String[] args) {
        new SpringApplication(BarCodeApplication.class).run(args);
    }
}
