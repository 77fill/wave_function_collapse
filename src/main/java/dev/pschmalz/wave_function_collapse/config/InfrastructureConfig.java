package dev.pschmalz.wave_function_collapse.config;

import dev.pschmalz.wave_function_collapse.infrastructure.ResourceStoreImpl;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ResourceStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.List;

@Configuration
public class InfrastructureConfig {
    @Value("${infrastructure.contained-resources.choose-file-name-suffixes}")
    private List<String> allowedFileNameSuffixes;

    @Bean
    public ResourceStore resourceStore() throws IOException {
        return ResourceStoreImpl.withAllowedFileNameSuffixes(allowedFileNameSuffixes);
    }
}
