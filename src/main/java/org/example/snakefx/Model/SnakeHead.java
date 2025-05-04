package org.example.snakefx.Model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static javafx.scene.paint.Color.RED;

public class SnakeHead {
    Direction direction = null;
    int lengthOfSnake;
    int snakeHeadPositionX;
    int snakeHeadPositionY;
    private final int snakeSize = 50;
    private Rectangle snakehead ;
    private Color snakeheadColor;

    public SnakeHead(Direction direction, int lengthOfSnake, int x, int y, Color snakeheadColor) {
        this.direction = direction;
        this.lengthOfSnake = lengthOfSnake;
        this.snakeHeadPositionX = snakeHeadPositionX;
        this.snakeHeadPositionY = snakeHeadPositionY;
        this.snakehead= new Rectangle(x,y,snakeSize,snakeSize);
        this.snakehead.setFill(snakeheadColor);
    }

    public Rectangle getNode(){
        return this.snakehead;
    }

    public Direction getDirection() { return direction; }

    public void setDirection(Direction direction) { this.direction = direction; }

    public void checkCollision() {}

    public void move(int unitSize) {
        switch (direction) {
            case Up:
                snakehead.setY(snakehead.getY() - unitSize);
                break;
            case Down:
                snakehead.setY(snakehead.getY() + unitSize);
                break;
            case Left:
                snakehead.setX(snakehead.getX() - unitSize);
                break;
            case Right:
                snakehead.setX(snakehead.getX() + unitSize);
                break;
        }
    }








}