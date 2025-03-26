package dev.pschmalz.wave_function_collapse.machinery;

import dev.pschmalz.wave_function_collapse.model.ConstrainedTile;
import dev.pschmalz.wave_function_collapse.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class Constrainer {
    public List<ConstrainedTile> contrain(List<Tile> tiles) {
        List<ConstrainedTile> contrainedTiles = new ArrayList<>();

        for(var tile : tiles) {
            var contrainedTile = new ConstrainedTile(tile);

            

            contrainedTiles.add(contrainedTile);
        }

        return contrainedTiles;
    }
}
