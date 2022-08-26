package br.com.atech.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper ModelMapper() {
        ModelMapper modemMapper = new ModelMapper();
        return modemMapper;
    }
}
