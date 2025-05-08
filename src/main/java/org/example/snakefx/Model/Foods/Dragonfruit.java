package org.example.snakefx.Model.Foods;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.snakefx.Controller.GameMap;
import org.example.snakefx.Model.SnakeHead;

/**
 * a Dragonfruit class
 */
public class Dragonfruit extends Food {
    ImageView image;

    public Dragonfruit(int positionX, int positionY) {
        super(positionX, positionY, 100);
        Image dragonImage = new Image(getClass().getResourceAsStream("/Pictures/Dragonfruit.png"));
        this.image = new ImageView(dragonImage);
        this.image.setFitWidth(GameMap.UNIT_SIZE);
        this.image.setFitHeight(GameMap.UNIT_SIZE);
        this.image.setX(positionX);
        this.image.setY(positionY);
    }

    @Override
    public void eat() {
        SnakeHead snakeHead = GameMap.getSnakeHead();
        snakeHead.increaseSnakeSize(snakeHead.getSnakeSize() + 1);
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
