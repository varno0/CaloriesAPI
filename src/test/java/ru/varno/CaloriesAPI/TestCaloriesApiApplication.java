package ru.varno.CaloriesAPI;

import org.springframework.boot.SpringApplication;

public class TestCaloriesApiApplication {

    public static void main(String[] args) {
        SpringApplication.from(CaloriesApiApplication::main).with(TestcontainersConfiguration.class).run(args);
    }
}
