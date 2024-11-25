package org.example.groupproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication
@ComponentScan(basePackages = {"org.example.groupproject.applicant", "org.example.groupproject.CsvService"})
    @EnableJpaRepositories(basePackages = "org.example.groupproject.applicant")
    public class GroupProjectApplication {

        public static void main(String[] args) {
            SpringApplication.run(GroupProjectApplication.class, args);
        }
    }


