package dev.pschmalz.wave_function_collapse.config.converters;

import io.vavr.collection.List;
import io.vavr.collection.Stream;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToVavrList implements Converter<String, List<String>> {
    @Override
    public List<String> convert(String source) {
        return Stream.of(source.split(",")).map(String::trim).toList();
    }
}
