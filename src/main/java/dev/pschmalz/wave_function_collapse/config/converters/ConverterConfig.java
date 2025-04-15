package dev.pschmalz.wave_function_collapse.config.converters;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.ConversionService;
import org.springframework.format.support.DefaultFormattingConversionService;

@Configuration
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConverterConfig {
    @Autowired
    StringToVavrList stringToVavrList;

    @Bean
    public ConversionService conversionService() {
        var conversionService =  new DefaultFormattingConversionService();

        conversionService.addConverter(stringToVavrList);

        return conversionService;
    }
}
