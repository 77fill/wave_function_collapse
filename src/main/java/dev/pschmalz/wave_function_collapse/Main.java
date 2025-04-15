package dev.pschmalz.wave_function_collapse;

import dev.pschmalz.wave_function_collapse.config.lifecycle.ViewStartStop;
import dev.pschmalz.wave_function_collapse.config.property_sources.YamlPropertySourceFactory;
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
    //TODO project "standard" ...
    public static void main(String[] args) throws Exception {
        var appContext = new AnnotationConfigApplicationContext(Main.class);

        var viewStartStop = appContext.getBean(ViewStartStop.class);

        while(true) {
            if(viewStartStop.isRunning())
                Thread.sleep(200);
            else
                break;
        }

        appContext.stop();
    }
}
