package org.example.groupproject;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"org.example.groupproject.applicant", "org.example.groupproject.CsvService"})
@EnableJpaRepositories(basePackages = "org.example.groupproject.applicant")
@EnableScheduling
public class GroupProjectApplication {
    public static void main(String[] args) {
            SpringApplication.run(GroupProjectApplication.class, args);
    }
}


