package dev.pschmalz.wave_function_collapse.config;

import com.google.common.reflect.ClassPath;
import dev.pschmalz.wave_function_collapse.Main;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.io.IOException;
import java.util.Random;

@Configuration
public class GeneralConfig {
    @Bean
    public Random random() {
        return new Random();
    }

    @Bean
    public JFileChooser fileChooser() {
        var fileChooser = new JFileChooser();

        fileChooser.setMultiSelectionEnabled(true);

        return fileChooser;
    }

    @Bean
    public ClassPath classPath() throws IOException {
        return ClassPath.from(Main.class.getClassLoader());
    }
}
