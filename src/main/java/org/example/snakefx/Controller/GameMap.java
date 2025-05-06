package org.example.snakefx.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.snakefx.Model.*;
import org.example.snakefx.Model.Foods.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * class which sets up the game
 */
public class GameMap extends Pane {

    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * UNIT_SIZE) / UNIT_SIZE;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    boolean isRunning = false;
    private SnakeHead snakeHead;
    private List<Food> foods = new ArrayList<>();
    int fruitsToSpawn = 3;
    public boolean freeToMove = true;
    private Score score;
    private GameTime gameTime;
    private Timeline timeline;

    private Canvas canvas;
    private GraphicsContext gc;

    public GameMap() {
        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);

        // Set preferred size for proper layout
        this.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    /**
     * draws grid
     */
    public void draw() {
        gc.clearRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        if (isRunning) {
            gc.setStroke(Color.GREEN);
            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
                // Vertical lines
                gc.strokeLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
                // Horizontal lines
                gc.strokeLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
            }
        }
    }

    /**
     * starts the game and initializes necessary participants and starts timeline
     */
    public void startGame() {
        isRunning = true;
        draw();
        spawnSnake();
        spawnFood(fruitsToSpawn);
        initScore();

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.3), e -> update());
        timeline.getKeyFrames().add(keyFrame);

        timeline.play();
        initGameSpeedText();
    }

    /**
     * spawns the snake
     */
    private void spawnSnake() {
        snakeHead = new SnakeHead(Direction.Left, 3,UNIT_SIZE*10,UNIT_SIZE*10);
        this.getChildren().add(snakeHead.getNode());
        snakeHead.parent = this;

    }

    /**
     * Mikkel????
     */
    public void update()
    {
        snakeHead.tick();
        snakeHead.move();
        checkIfSnakeIsOnTopOfFoodAndIsHellaHungry();
        freeToMove = true;

        gameTime.tick();
        System.out.println(((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) + " KB");
        Runtime.getRuntime().gc();
    }

    /**
     *Spawns the food on the map
     * @param fruitsToSpawn how many fruits that should be on the map
     */
    private void spawnFood(int fruitsToSpawn) {
        Random random = new Random();

        for (int i = 0; i < fruitsToSpawn; i++) {
            if (!isRunning) {
                return;
            }

            Food food;
            int chance = random.nextInt((100)+1);

            if (chance <= 40) {
                food = new Apple(
                        random.nextInt(Math.round((float) SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE,
                        random.nextInt(Math.round((float) SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE);

            }
            else if (chance <= 65) {
                food = new Banana(
                        random.nextInt(Math.round((float) SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE,
                        random.nextInt(Math.round((float) SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE);
            }
            else if (chance <= 90) {
                food = new Brick(
                        random.nextInt(Math.round((float) SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE,
                        random.nextInt(Math.round((float) SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE);
            }
            else {
                food = new Dragonfruit(
                        random.nextInt(Math.round((float) SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE,
                        random.nextInt(Math.round((float) SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE);

            }

            boolean positionOccupied = false;

            for( Food existingFood : foods) {
                if (existingFood.getPositionX() == food.getPositionX() && existingFood.getPositionY() == food.getPositionY()) {
                    positionOccupied = true;
                    System.out.println("Double Fruit Spawn prevented");
                    spawnFood(1);
                    break;
                }
            }

            for (SnakePart snakePart : snakeHead.getSnakeParts()) {
                if (snakePart.getX() == food.getPositionX() && snakePart.getY() == food.getPositionY()) {
                    positionOccupied = true;
                    System.out.println("Spawn on snake prevented");
                    spawnFood(1);
                    break;
                }
            }

            if (!positionOccupied) {
                this.getChildren().add(food.getImage());
                foods.add(food);
            }
        }

    }

    /**
     * sets snake direction
     * @param direction sets snake direction
     */
    public void setSnakeDirection(Direction direction) {
        if (snakeHead != null) {
            snakeHead.setDirection(direction);
        }
    }

    /**
     * initializing score text
     */
    public void initScore() {
        score = new Score(snakeHead.getLengthOfSnake()-3);
        this.getChildren().add(score.getNode());
    }

    /**
     * initializing game speed text
     */
    public void initGameSpeedText() {
        gameTime = new GameTime(this);
        this.getChildren().add(gameTime.getNode());
    }

    /**
     * method to eat the fruit and changes game speed according to the fruits eaten
     */
    public void checkIfSnakeIsOnTopOfFoodAndIsHellaHungry() {
        int sx = snakeHead.getSnakeHeadPositionX();
        int sy = snakeHead.getSnakeHeadPositionY();

        for (int i = 0; i < foods.size(); i++) {
            int fx = foods.get(i).getPositionX();
            int fy = foods.get(i).getPositionY();

            if (sx == fx && sy == fy) {

                if (foods.get(i).getClass() == Dragonfruit.class) {

                    getChildren().remove(foods.get(i).getImage());
                    foods.remove(foods.get(i));
                    snakeHead.addToLengthOfSnake(3);
                    spawnFood(2);

                    // replaces gamespeed
                    timeline.stop();

                    timeline.getKeyFrames().clear();

                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.1), e -> update());
                    timeline.getKeyFrames().add(keyFrame);

                    timeline.play();
                }
                if (foods.get(i).getClass() == Brick.class) {

                    getChildren().remove(foods.get(i).getImage());
                    foods.remove(foods.get(i));
                    snakeHead.addToLengthOfSnake(-3);
                    spawnFood(1);

                    // replaces gamespeed
                    timeline.stop();
                    timeline.getKeyFrames().clear();
                    KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.4), e -> update());
                    timeline.getKeyFrames().add(keyFrame);

                    timeline.play();
                }

                else {
                    //remove food in JavaFX
                    getChildren().remove(foods.get(i).getImage());
                    foods.remove(foods.get(i));
                    //increase length to snake
                    snakeHead.addToLengthOfSnake(1);
                    //spawn new food
                    spawnFood(1);
                }
            }
        }
    }


}
