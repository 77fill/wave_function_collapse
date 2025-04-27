package dev.pschmalz.wave_function_collapse.usecase;

import dev.pschmalz.wave_function_collapse.domain.basic_elements.Image;
import dev.pschmalz.wave_function_collapse.domain.basic_elements.Tile;
import dev.pschmalz.wave_function_collapse.domain.workers.ConstraintAppender;
import dev.pschmalz.wave_function_collapse.domain.workers.ThreeByThree;
import dev.pschmalz.wave_function_collapse.domain.workers.TileSlotGridGenerator;
import io.vavr.collection.Set;
import io.vavr.collection.Stream;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestWaveFunctionCollapse {
    WaveFunctionCollapse waveFunctionCollapse;

    @Mock
    ChooseTileImages chooseTileImages;

    AutoCloseable closeable;

    @Test
    public void givenExampleImages_getTileSlotGrid() {
        var exampleImageTiles = exampleImagesInsideResources("wfc1.png","wfc2.png","wfc3.png","wfc4.png");

        when(chooseTileImages.get())
                .thenReturn(
                    exampleImageTiles
                );

        var resultGrid = waveFunctionCollapse.await().get();

        assertEquals(20, resultGrid.getWidth());
        assertEquals(20, resultGrid.getHeight());
        assertEquals(exampleImageTiles, resultGrid.getTiles());
    }

    private Set<Tile> exampleImagesInsideResources(String...filenames) {
        return Stream.of(filenames)
                .map(this::exampleImageInsideResources)
                .toSet();
    }

    private Tile exampleImageInsideResources(String filename) {
        var inputStream = TestWaveFunctionCollapse.class.getClassLoader().getResourceAsStream("dev/pschmalz/wave_function_collapse/examples/"+filename);

        if(inputStream == null)
            throw new IllegalStateException("classLoader did not find the file");

        try {
            var bufferedImage = ImageIO.read(inputStream);
            var image = new Image(bufferedImage);
            return image.toTile();

        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Before
    public void init() {
        var generateTileConstraints = new GenerateTileConstraints(new ConstraintAppender(new ThreeByThree()));
        var tileSlotGridGenerator = new TileSlotGridGenerator(new Random());
        var domainWfc = new dev.pschmalz.wave_function_collapse.domain.workers.WaveFunctionCollapse();

        closeable = MockitoAnnotations.openMocks(this);

        waveFunctionCollapse = new WaveFunctionCollapse(
                tileSlotGridGenerator,
                domainWfc,
                chooseTileImages,
                generateTileConstraints);
    }

    @After
    public void cleanUp() throws Exception {
        closeable.close();
    }
}
