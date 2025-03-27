package dev.pschmalz.wave_function_collapse.domain;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;

public class FieldManager {
    private Field[][] fields;

    public Optional<Field> get(int x, int y) {
        if(x < 0 || y < 0 || x >= fields.length || y >= fields[0].length)
            return Optional.empty();

        return Optional.of(fields[x][y]);
    }
}
