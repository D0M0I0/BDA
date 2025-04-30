package com.example.h2demo;

import com.example.h2demo.repository.AdherentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.example.h2demo.entities.*;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class H2demoApplication {

    public static void main(String[] args) {
        SpringApplication.run(H2demoApplication.class, args);
    }
    @Bean
    CommandLineRunner runner(AdherentRepository repository)
    {
        return args ->
        {
            repository.save(new Adherent(null, "A", "A", 30));
            repository.save(new Adherent(null, "B", "B", 25));
            repository.save(new Adherent(null, "C", "C", 35));
            repository.save(new Adherent(null, "D", "D", 40));
        };
    }
}
