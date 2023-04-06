package com.example.client;
// конфигурационный класс
// для объединени всех методов в одно Spring boot приложение
// все зависимости будут взаимодействовать во всех классах
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.context.annotation.Configuration; // for creating the bean for this class
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry; // allows to create simple automated controllers pre-configured with status code and/or a view.
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer; // метод конфигурации
@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
//        registry.addViewController("/").setViewName("home");
        registry.addViewController("/").setViewName("index");
//        registry.addViewController("/hello").setViewName("hello");
        registry.addViewController("/auth/authenticate").setViewName("login");
    }
}
