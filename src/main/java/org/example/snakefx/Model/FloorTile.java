package org.example.snakefx.Model;

import javafx.scene.image.ImageView;
import org.example.snakefx.Controller.GameMap;

public class FloorTile {
    ImageView image;

    public FloorTile(ImageView image) {
        image.setFitWidth(GameMap.UNIT_SIZE);
        image.setFitHeight(GameMap.UNIT_SIZE);
        this.image = image;
    }
    public ImageView getImage() {
        return image;
    }
}

