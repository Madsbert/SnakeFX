package org.example.snakefx.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import org.example.snakefx.Controller.GameMap;

import java.util.Objects;

import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.RED;

public class SnakeHead {
    Direction direction = null;
    int lengthOfSnake;
    int snakeHeadPositionX;
    int snakeHeadPositionY;
    private final int snakeSize = 25;
    private ImageView snakehead;
    private final Rotate rotate;
    private List<SnakePart> snakeParts;
    public Object parent;


    public SnakeHead(Direction direction, int lengthOfSnake, int x, int y) {
        this.direction = direction;
        this.lengthOfSnake = lengthOfSnake;
        this.snakeHeadPositionX = x;
        this.snakeHeadPositionY = y;

        Image snakeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/Snakehead.png")));
        this.snakehead = new ImageView(snakeImage);
        this.snakehead.setFitWidth(snakeSize);
        this.snakehead.setFitHeight(snakeSize);

        this.rotate = new Rotate(0,snakeSize/2.0,snakeSize/2.0);

        this.snakeParts = new ArrayList<>();
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

        // Death upon hitting tail
        for (SnakePart snakePart : snakeParts) {
            if (snakePart.getX() == snakeHeadPositionX && snakePart.getY() == snakeHeadPositionY) {
                Runtime.getRuntime().exit(0);
            }
        }

        // Death upon leaving grid area
        if ((snakeHeadPositionX >= GameMap.SCREEN_WIDTH || snakeHeadPositionY >= GameMap.SCREEN_HEIGHT)
        || (snakeHeadPositionX < 0 || snakeHeadPositionY < 0)) {
            Runtime.getRuntime().exit(0);
        }

        // Sæt ny position
        snakehead.setX(snakeHeadPositionX);
        snakehead.setY(snakeHeadPositionY);
    }

    public void tick()
    {
        SnakePart newPart = new SnakePart(lengthOfSnake,snakeHeadPositionX,snakeHeadPositionY, this);
        snakeParts.add(newPart);

        for (int i = 0; i < snakeParts.size(); i++) {
            snakeParts.get(i).tick();

            if (!snakeParts.get(i).alive)
            {
                snakeParts.remove(i);
                i--;
            }
        }
    }

    public int getLengthOfSnake() {
        return lengthOfSnake;
    }
}








