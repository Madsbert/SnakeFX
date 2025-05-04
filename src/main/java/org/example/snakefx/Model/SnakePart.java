package org.example.snakefx.Model;

public class SnakePart {
    private int liftTime;
    int snakePartPositionX;
    int snakePartPositionY;

    public SnakePart(int liftTime, int snakePartPositionX, int snakePartPositionY) {
        this.liftTime = liftTime;
        this.snakePartPositionX = snakePartPositionX;
        this.snakePartPositionY = snakePartPositionY;
    }

    public void deathOfPart(){}

    public int getLiftTime() {
        return liftTime;
    }

    public void snakePartSprite(){}
}
