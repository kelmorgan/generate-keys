package com.kelmorgan.generatekeypair;

import com.kelmorgan.generatekeypair.service.GenerateKeys;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@RequiredArgsConstructor
public class GenerateKeyPairApplication {


    public static void main(String[] args) {
        SpringApplication.run(GenerateKeyPairApplication.class, args);
    }


    @Bean
    CommandLineRunner runner(GenerateKeys generateKeys) {
        return run -> {
            generateKeys.generate();
        };
    }

}
