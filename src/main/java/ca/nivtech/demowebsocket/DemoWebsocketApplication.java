package ca.nivtech.demowebsocket;

import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.Collections;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories
public class DemoWebsocketApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoWebsocketApplication.class, args);
    }

    @Bean
    public com.fasterxml.jackson.databind.Module datatypeHibernateModule()
    {
        return new Hibernate5Module();
    }
}
