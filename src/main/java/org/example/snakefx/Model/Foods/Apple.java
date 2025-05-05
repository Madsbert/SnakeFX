package org.example.snakefx.Model.Foods;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.snakefx.Controller.GameMap;

public class Apple extends Food {
    ImageView image;

    public Apple(int positionX, int positionY) {
        super(positionX, positionY, 10);
        Image appleImage = new Image(getClass().getResourceAsStream("/Pictures/Apple.png"));
        this.image = new ImageView(appleImage);
        this.image.setFitWidth(GameMap.UNIT_SIZE);
        this.image.setFitHeight(GameMap.UNIT_SIZE);
        this.image.setX(positionX);
        this.image.setY(positionY);

    }

    public ImageView getImage() {
        return this.image;
    }
    @Override
    public void getsEaten() {

    }
}
