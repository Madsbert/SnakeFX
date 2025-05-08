package org.example.snakefx.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import org.example.snakefx.Controller.GameMap;

import java.util.Objects;

/**
 * class which handles the snakepart
 */
public class SnakePart extends ImageView {
    private int lifeTime;
    public boolean alive;
    public SnakeHead parent;
    private Pane pane;

    public SnakePart(int lifeTime, int snakePartPositionX, int snakePartPositionY, SnakeHead parent) {
        this.lifeTime = lifeTime;
        alive = true;

        this.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/SnakeBody.png"))));
        this.setFitWidth(GameMap.UNIT_SIZE);
        this.setFitHeight(GameMap.UNIT_SIZE);
        this.setX(snakePartPositionX);
        this.setY(snakePartPositionY);

        switch (parent.direction) {
            case Up:
                setRotate(180);
                break;
            case Down:
                setRotate(0);
                break;
            case Left:
                setRotate(90);
                break;
            case Right:
                setRotate(270);
                break;
        }

        this.parent = parent;

        if (this.parent.parent != null && this.parent.parent instanceof Pane parentPane) {
            parentPane.getChildren().add(this);
            pane = parentPane;
        }
    }

    /**
     * removes snakeparts from the lifetime of the snake part has expired
     */
    public void tick()
    {
        lifeTime--;
        if (lifeTime <= 0){
            alive = false;
            pane.getChildren().remove(this);
        }
    }
}
