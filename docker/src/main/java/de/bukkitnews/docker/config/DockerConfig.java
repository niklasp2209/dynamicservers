package de.bukkitnews.docker.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DockerConfig {

    @Bean
    public @NotNull String containerType() {
        return "docker";
    }
}

