package org.example.snakefx.Model.Foods;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.snakefx.Controller.GameMap;

public class Weed extends Food {
    ImageView image;

    public Weed(int positionX, int positionY) {
        super(positionX, positionY, 1);
        Image dragonImage = new Image(getClass().getResourceAsStream("/Pictures/Weed.png"));
        this.image = new ImageView(dragonImage);
        this.image.setFitWidth(GameMap.UNIT_SIZE);
        this.image.setFitHeight(GameMap.UNIT_SIZE);
        this.image.setX(positionX);
        this.image.setY(positionY);

    }

    public void eat()
    {

    }

    /**
     * method to return the imageview
     * @return an imageview
     */
    @Override
    public ImageView getImage() {
        return this.image;
    }
}
