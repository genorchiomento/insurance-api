package io.github.genorchiomento.insuranceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class InsuranceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(InsuranceApiApplication.class, args);
    }

}
