package com.sso.app2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class app2 {
    public static void main(String[] args){
        SpringApplication.run(app2.class,args);
    }
    @Bean
    public RestTemplate restTemplate(){
        return  new RestTemplate();
    }
}