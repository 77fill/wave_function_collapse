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

import javax.swing.*;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Configuration
public class InfrastructureConfig {
    @Value("${bundled-resources.allowed-name-suffixes}")
    private List<String> allowedNameSuffixes;
    @Value("${bundled-resources.base-path}")
    private Path resourcesBasePath;
    @Value("${file-system.temp-dir-prefix}")
    private String tempDirPrefix;
    @Autowired
    private ClassPath classPath;
    @Autowired
    private JFileChooser jFileChooser;

    @Bean
    public ClasspathStore classpathStore() {
        return new ClasspathStoreImpl(allowedNameSuffixes, classPath, resourcesBasePath);
    }

    @Bean
    public FileChooser fileChooser() {
        return new FileChooserImpl(jFileChooser);
    }

    @Bean
    public FileSystemStore fileSystemStore() throws IOException {
        return new FileSystemStoreImpl(
                FileSystems.getDefault(),
                Files.createTempDirectory(tempDirPrefix));
    }
}
