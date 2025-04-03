package dev.pschmalz.wave_function_collapse.domain_workers.constrain_three_x_three;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Function;
import com.google.common.base.Throwables;
import dev.pschmalz.wave_function_collapse.domain.abstractions.FromTileSlotDirection_ToConstraint;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Image;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.collections_tuples.TileSlotGrid;
import dev.pschmalz.wave_function_collapse.domain.workers.ConstraintCreator;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ConstrainThreeXThree implements ConstraintCreator {
    private TileSlotGrid grid;
    private List<Tile> tiles;
    private Stream<Tile> tiles() {return tiles.stream();}

    @Override
    public void accept(TileSlotGrid grid) {
        this.grid = grid;

        loadTiles();
        checkImages();
        tiles().forEach(this::createConstraints);
    }

    private void createConstraints(Tile origin) {
        origin.setConditionalConstraints(
                tiles()
                    .flatMap(constraintCreatorWithOrigin(origin))
                    .collect(Collectors.toSet()));
    }

    private Function<Tile, Stream<FromTileSlotDirection_ToConstraint>> constraintCreatorWithOrigin(Tile origin) {
        return otherTile ->
                Stream.of();
    }


    private void checkImages() {
        checkArgument(
                tiles().map(Tile::getImage).allMatch(this::isThreeXThree),
                "This ConstraintCreator needs 3 x 3 tile images");
    }

    private void loadTiles() {
        tiles = grid.getTileSet().stream().toList();
    }

    private boolean isThreeXThree(Image image) {
        return image.getWidth() == 3
                && image.getHeight() == 3;
    }
}
