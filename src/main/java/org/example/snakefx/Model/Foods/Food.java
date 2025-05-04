package org.example.snakefx.Model.Foods;

public abstract class Food {
    int positionX;
    int positionY;
    int foodValue;

    public Food(int positionX, int positionY, int foodValue) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.foodValue = foodValue;
    }

    public abstract void getsEaten();

    public int getPositionX() { return positionX; }
    public int getPositionY() { return positionY; }
}