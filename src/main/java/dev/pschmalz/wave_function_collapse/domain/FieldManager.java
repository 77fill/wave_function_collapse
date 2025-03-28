package dev.pschmalz.wave_function_collapse.domain;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FieldManager {
    private Field[][] fields;

    public Optional<Field> get(int x, int y) {
        if(x < 0 || y < 0 || x >= fields.length || y >= fields[0].length)
            return Optional.empty();

        return Optional.of(fields[x][y]);
    }
}
