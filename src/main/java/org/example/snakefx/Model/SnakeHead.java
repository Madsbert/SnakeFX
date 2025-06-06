package org.example.snakefx.Model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import org.example.snakefx.Controller.GameMap;

import java.util.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * class to create a snakehead and handle its methods
 */
public class SnakeHead {
    Direction direction;
    int lengthOfSnake;
    int snakeHeadPositionX;
    int snakeHeadPositionY;
    private float snakeSize = 1;
    private ImageView snakehead;
    private final Rotate rotate;
    private List<SnakePart> snakeParts;
    public Object parent;
    private Runnable onDeath;
    public Rectangle rect;

    public SnakeHead(Direction direction, int lengthOfSnake, int x, int y) {
        this.direction = direction;
        this.lengthOfSnake = lengthOfSnake;
        this.snakeHeadPositionX = x;
        this.snakeHeadPositionY = y;

        rect = new Rectangle(snakeHeadPositionX, snakeHeadPositionY, snakeSize * GameMap.UNIT_SIZE, snakeSize * GameMap.UNIT_SIZE);

        Image snakeImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/Snakehead.png")));
        this.snakehead = new ImageView(snakeImage);
        this.snakehead.setFitWidth(snakeSize * GameMap.UNIT_SIZE);
        this.snakehead.setFitHeight(snakeSize * GameMap.UNIT_SIZE);

        this.rotate = new Rotate(0,snakeSize/2.0,snakeSize/2.0);

        this.snakeParts = new ArrayList<>();
        snakehead.getTransforms().add(rotate);
    }

    /**
     * Gets size of snakehead. Default is 1.
     * @return the snakesize
     */
    public float getSnakeSize() {
        return snakeSize;
    }

    /**
     * Sets size of snakehead. Default is 1.
     * @param snakeSize the length of the snake
     */
    public void increaseSnakeSize(float snakeSize) {
        if (this.snakeSize + snakeSize >= 2.25f)
        {
            this.snakeSize = 2.25f;
        }
        else
        {
            this.snakeSize += snakeSize;
        }
    }

    /**
     * method to return the imageview
     * @return an imageview
     */
    public ImageView getNode(){
        return this.snakehead;
    }

    /**
     * method to set the direction and prevent it from impossible movements
     * @param newDirection the direction the snake is heading
     */
    public void setDirection(Direction newDirection) {
        if ((this.direction == Direction.Up && newDirection == Direction.Down) ||
                (this.direction == Direction.Down && newDirection == Direction.Up) ||
                (this.direction == Direction.Left && newDirection == Direction.Right) ||
                (this.direction == Direction.Right && newDirection == Direction.Left)) {
            return;
        }
        this.direction = newDirection;
    }

    public void setOnDeath(Runnable onDeath) { this.onDeath = onDeath; }

    /**
     * method to get the x postion
     * @return x postion of the head
     */
    public int getSnakeHeadPositionX(){
        return this.snakeHeadPositionX;
    }

    /**
     * method to get the Y postion
     * @return Y postion of the head
     */
    public int getSnakeHeadPositionY(){
        return this.snakeHeadPositionY;
    }

    /**
     * method to move the snake and rotate and checks for collision and dies
     */
    public void move() {
        switch (direction) {
            case Up:
                snakeHeadPositionY -= GameMap.UNIT_SIZE;
                snakehead.setRotate(180);
                break;
            case Down:
                snakeHeadPositionY += GameMap.UNIT_SIZE;
                snakehead.setRotate(0);
                break;
            case Left:
                snakeHeadPositionX -= GameMap.UNIT_SIZE;
                snakehead.setRotate(90);
                break;
            case Right:
                snakeHeadPositionX += GameMap.UNIT_SIZE;
                snakehead.setRotate(270);
                break;
        }

        // Death upon hitting tail
        for (SnakePart snakePart : snakeParts) {
            if (snakePart.getX() == snakeHeadPositionX && snakePart.getY() == snakeHeadPositionY) {
                if (parent instanceof GameMap gameMap)
                {
                    die();
                    gameMap.gameTime.setModifier(0);
                }
            }
        }

        // Death upon leaving grid area
        if ((snakeHeadPositionX >= GameMap.SCREEN_WIDTH || snakeHeadPositionY >= GameMap.SCREEN_HEIGHT)
        || (snakeHeadPositionX < 0 || snakeHeadPositionY < 0)) {

            if (parent instanceof GameMap gameMap) {
                die();
                gameMap.gameTime.setModifier(0);
            }
        }

        // Sæt ny position

        this.snakehead.setFitWidth(GameMap.UNIT_SIZE * snakeSize);
        this.snakehead.setFitHeight(GameMap.UNIT_SIZE * snakeSize);
        this.snakehead.setScaleX(snakeSize);
        this.snakehead.setScaleY(snakeSize);

        if (snakeSize == 1)
        {
            this.snakehead.setX(snakeHeadPositionX);
            this.snakehead.setY(snakeHeadPositionY);
        }
        else
        {
            this.snakehead.setX(snakeHeadPositionX - (GameMap.UNIT_SIZE * (snakeSize - 1))/2);
            this.snakehead.setY(snakeHeadPositionY - (GameMap.UNIT_SIZE * (snakeSize - 1))/2);
        }


        rect.setWidth(GameMap.UNIT_SIZE * snakeSize);
        rect.setHeight(GameMap.UNIT_SIZE * snakeSize);
        rect.setScaleX(snakeSize);
        rect.setScaleY(snakeSize);
        if (snakeSize == 1)
        {
            rect.setX(snakeHeadPositionX);
            rect.setY(snakeHeadPositionY);
        }
        else
        {
            rect.setX(snakeHeadPositionX - (GameMap.UNIT_SIZE * (snakeSize - 1))/2);
            rect.setY(snakeHeadPositionY - (GameMap.UNIT_SIZE * (snakeSize - 1))/2);
        }

    }

    /**
     * method to make the snake longer
     */
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

    /**
     * method to get the snake length
     * @return length of snake
     */
    public int getLengthOfSnake() {
        return lengthOfSnake;
    }

    /**
     * method to add to int length of snake
     * @param add to the int length of the snake
     */
    public void addToLengthOfSnake(int add){
        lengthOfSnake += add;
    }

    /**
     * method to get snake parts
     * @return list of snakeparts
     */
    public List<SnakePart> getSnakeParts() {
        return snakeParts;
    }

    /**
     * method to run the runnable onDeath
     */
    public void die() {
        if (onDeath != null) {
            onDeath.run();
        }
    }

    public ImageView getImageView() {
        return snakehead;
    }
}








