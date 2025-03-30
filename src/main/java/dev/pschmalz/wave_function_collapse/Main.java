package dev.pschmalz.wave_function_collapse;

import dev.pschmalz.wave_function_collapse.config.YamlPropertySourceFactory;
import dev.pschmalz.wave_function_collapse.infrastructure.view.DisplayExecutor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@ComponentScan
@PropertySource(value = "classpath:config.yml", factory = YamlPropertySourceFactory.class)
@EnableAsync
public class Main {

    public static void main(String[] args) throws Exception {
        var appContext = new AnnotationConfigApplicationContext(Main.class);
        var display = appContext.getBean(DisplayExecutor.class);
        while (!display.isShutdown()) {
            Thread.sleep(200);
        }
        appContext.stop();
    }
}
