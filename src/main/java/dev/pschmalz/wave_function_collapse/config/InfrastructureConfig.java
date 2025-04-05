package dev.pschmalz.wave_function_collapse.config;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.infrastructure.classpath_store.ClasspathStoreImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.file_chooser.FileChooserImpl;
import dev.pschmalz.wave_function_collapse.infrastructure.filesystem_store.FileSystemStoreImpl;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.ClasspathStore;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileChooser;
import dev.pschmalz.wave_function_collapse.usecase.interfaces.FileSystemStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Configuration
public class InfrastructureConfig {
    @Value("${infrastructure.contained-resources.choose-file-name-suffixes}")
    private List<String> allowedNameSuffixes;
    @Autowired
    private ClassPath classPath;

    @Bean
    public ClasspathStore classpathStore() {
        return new ClasspathStoreImpl(allowedNameSuffixes, classPath, Path.of("dev", "pschmalz", "wave_function_collapse"));
    }

    @Bean
    public FileChooser fileChooser() {
        return new FileChooserImpl();
    }

    @Bean
    public FileSystemStore fileSystemStore() throws IOException {
        return new FileSystemStoreImpl(
                FileSystems.getDefault(),
                Files.createTempDirectory("wave_function_collapse"));
    }
}
