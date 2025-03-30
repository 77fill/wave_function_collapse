package dev.pschmalz.wave_function_collapse.infrastructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import processing.core.PApplet;
import processing.core.PImage;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class Images {
    @Autowired
    private PApplet pApplet;
    private Map<String, PImage> imageByPath = new HashMap<>();

    public Optional<PImage> get(String path) {
        var image = Optional.ofNullable(imageByPath.get(path));

        if(image.isEmpty())
            image = Optional.ofNullable(pApplet.loadImage(path));

        image.ifPresent(i -> imageByPath.put(path,i));

        return image;
    }

    public Optional<PImage> get(File file) {
        return get(file.toString());
    }
}
