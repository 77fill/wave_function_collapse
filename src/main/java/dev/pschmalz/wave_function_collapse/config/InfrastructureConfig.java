package dev.pschmalz.wave_function_collapse.config;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.infrastructure.ClasspathStoreImpl;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class InfrastructureConfig {
    @Value("${infrastructure.contained-resources.choose-file-name-suffixes}")
    private List<String> allowedNameSuffixes;
    @Autowired
    private ClassPath classPath;

    @Bean
    public ClasspathStore classpathStore() {
        return new ClasspathStoreImpl(allowedNameSuffixes, classPath);
    }
}
