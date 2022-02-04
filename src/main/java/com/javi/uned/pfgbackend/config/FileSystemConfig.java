package com.javi.uned.pfgbackend.config;

import com.javi.uned.pfgbackend.adapters.filesystem.FileServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileSystemConfig {

    @Bean
    public FileServiceImpl fileService() {
        return new FileServiceImpl();
    }

}
