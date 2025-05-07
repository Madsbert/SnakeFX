package org.example.snakefx.Model.Foods;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

/**
 * abstract class that gives all foods some methods
 */
public abstract class Food {
    int positionX;
    int positionY;
    int foodValue;
    public int lifetime = 35;

    public Food(int positionX, int positionY, int foodValue ) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.foodValue = foodValue;

    }

    public void tick()
    {
        lifetime--;
    }

    /**
     * method to return the imageview
     * @return an imageview
     */
    public abstract ImageView getImage();

    /**
     * get the x position
     * @return the x postion of a food object
     */
    public int getPositionX() { return positionX; }
    /**
     * get the Y position
     * @return the Y postion of a food object
     */
    public int getPositionY() { return positionY; }
}