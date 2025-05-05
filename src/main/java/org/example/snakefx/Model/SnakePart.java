package org.example.snakefx.Model;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.example.snakefx.Controller.GameMap;

public class SnakePart extends Rectangle {
    private int lifeTime;
    public boolean alive;
    public SnakeHead parent;
    private Pane pane;

    public SnakePart(int lifeTime, int snakePartPositionX, int snakePartPositionY, SnakeHead parent) {
        this.lifeTime = lifeTime - 1;
        alive = true;
        this.setHeight(GameMap.UNIT_SIZE);
        this.setWidth(GameMap.UNIT_SIZE);
        this.setX(snakePartPositionX);
        this.setY(snakePartPositionY);
        this.setFill(Color.RED);
        this.parent = parent;

        if (this.parent.parent != null && this.parent.parent instanceof Pane parentPane) {
            parentPane.getChildren().add(this);
            pane = parentPane;
        }
    }

    public void deathOfPart(){}

    public void tick()
    {
        lifeTime--;
        if (lifeTime == 0){
            alive = false;
            pane.getChildren().remove(this);
        }
    }

    public int getLiftTime() {
        return lifeTime;
    }

    public void snakePartSprite(){}
}
