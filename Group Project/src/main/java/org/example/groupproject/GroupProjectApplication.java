package org.example.groupproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class GroupProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(GroupProjectApplication.class, args);
    }

}
