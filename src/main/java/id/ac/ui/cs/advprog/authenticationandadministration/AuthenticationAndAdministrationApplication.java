package id.ac.ui.cs.advprog.authenticationandadministration;

import id.ac.ui.cs.advprog.authenticationandadministration.core.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class AuthenticationAndAdministrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthenticationAndAdministrationApplication.class, args);
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new CorsConfig();
    }
}
