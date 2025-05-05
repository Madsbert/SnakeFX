package org.example.snakefx.Model.Foods;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.snakefx.Controller.GameMap;

public class Brick extends Food {
    ImageView image;

    public Brick(int positionX, int positionY) {
        super(positionX, positionY, 100);
        Image dragonImage = new Image(getClass().getResourceAsStream("/Pictures/Brick.png"));
        this.image = new ImageView(dragonImage);
        this.image.setFitWidth(GameMap.UNIT_SIZE);
        this.image.setFitHeight(GameMap.UNIT_SIZE);
        this.image.setX(positionX);
        this.image.setY(positionY);

    }


    @Override
    public void getsEaten() {

    }

    @Override
    public ImageView getImage() {
        return this.image;
    }
}
