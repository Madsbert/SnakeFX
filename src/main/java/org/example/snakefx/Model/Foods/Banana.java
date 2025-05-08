package org.example.snakefx.Model.Foods;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.snakefx.Controller.GameMap;

/**
 * a banana class
 */
public class Banana extends Food {
    ImageView image;
    public Banana(int positionX, int positionY) {
        super(positionX, positionY, 20);
        Image bananaImage = new Image(getClass().getResourceAsStream("/Pictures/Banana.png"));
        this.image = new ImageView(bananaImage);
        this.image.setFitWidth(GameMap.UNIT_SIZE);
        this.image.setFitHeight(GameMap.UNIT_SIZE);
        this.image.setX(positionX);
        this.image.setY(positionY);
    }


    @Override
    public void eat() {
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
