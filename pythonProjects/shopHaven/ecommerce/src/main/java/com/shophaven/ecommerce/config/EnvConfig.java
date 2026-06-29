package com.shophaven.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;

@Configuration
public class EnvConfig {
    
    @Bean
    public Dotenv dotenv(){
        Dotenv dotenv = Dotenv.load();
        String serverPort = dotenv.get("SERVER_PORT");
        System.out.println("Server Port: " + serverPort);
        return dotenv;
    }
}
