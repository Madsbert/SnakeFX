package org.example.snakefx.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;

import java.util.Objects;

import static javafx.scene.paint.Color.RED;

public class SnakeHead {
    Direction direction = null;
    int lengthOfSnake;
    int snakeHeadPositionX;
    int snakeHeadPositionY;
    private final int snakeSize = 50;
    private ImageView snakehead;
    private final Rotate rotate;


    public SnakeHead(Direction direction, int lengthOfSnake, int x, int y, Color snakeheadColor) {
        this.direction = direction;
        this.lengthOfSnake = lengthOfSnake;
        this.snakeHeadPositionX = x;
        this.snakeHeadPositionY = y;

        Image snakeImage = new Image(getClass().getResourceAsStream("/Pictures/Snakehead.png"));
        this.snakehead = new ImageView(snakeImage);
        this.snakehead.setFitWidth(snakeSize);
        this.snakehead.setFitHeight(snakeSize);

        this.rotate = new Rotate(0,snakeSize/2.0,snakeSize/2.0);
        snakehead.getTransforms().add(rotate);
    }

    public ImageView getNode(){
        return this.snakehead;
    }

    public Direction getDirection() { return direction; }

    public void setDirection(Direction newDirection) {
        if ((this.direction == Direction.Up && newDirection == Direction.Down) ||
                (this.direction == Direction.Down && newDirection == Direction.Up) ||
                (this.direction == Direction.Left && newDirection == Direction.Right) ||
                (this.direction == Direction.Right && newDirection == Direction.Left)) {
            return;
        }
        this.direction = newDirection;
    }

    public void checkCollision() {}

    public void move(int unitSize) {
        switch (direction) {
            case Up:
                snakeHeadPositionY -= unitSize;
                snakehead.setRotate(180);
                break;
            case Down:
                snakeHeadPositionY += unitSize;
                snakehead.setRotate(0);
                break;
            case Left:
                snakeHeadPositionX -= unitSize;
                snakehead.setRotate(90);
                break;
            case Right:
                snakeHeadPositionX += unitSize;
                snakehead.setRotate(270);
                break;
        }

        // Sæt ny position
        snakehead.setX(snakeHeadPositionX);
        snakehead.setY(snakeHeadPositionY);
    }


}








