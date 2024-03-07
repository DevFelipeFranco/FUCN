package com.fucn;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TechnologicalEquipmentLoanManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechnologicalEquipmentLoanManagementApplication.class, args);
    }

    @Bean
    CommandLineRunner init(PasswordEncoder encoder) {
        return args -> System.out.println(encoder.encode("franco"));
    }
}
