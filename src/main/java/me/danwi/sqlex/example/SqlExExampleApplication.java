package me.danwi.sqlex.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SqlExExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(SqlExExampleApplication.class, args);
    }
}
