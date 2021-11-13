package com.herokuapp.trello_inspired_app.trelloinspiredappbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication (exclude = {
        org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class}
)
public class TrelloInspiredAppBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(TrelloInspiredAppBackendApplication.class, args);
    }

}
