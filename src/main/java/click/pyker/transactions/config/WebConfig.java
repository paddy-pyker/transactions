package click.pyker.transactions.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins("https://pyker.click") 
                .allowedMethods("GET", "POST", "PUT", "DELETE") 
                .allowedHeaders("*")
                .allowCredentials(true).maxAge(3600); ;
            }
        };
    }
}
