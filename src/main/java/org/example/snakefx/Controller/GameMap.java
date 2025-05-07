package org.example.snakefx.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.snakefx.Game;
import org.example.snakefx.Model.*;
import org.example.snakefx.Model.Foods.*;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    int fruitsToSpawn = 10;
    int amountOfBricks = 0;
    public boolean freeToMove = true;
    private Score score;
    public GameTime gameTime;
    private GameOver gameOver;
    public Random random = new Random();
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
     * draws FloorTiles
     */
    public void draw() {
        List<Image> tileImages = new ArrayList<>();

        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile1.png"))));
        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile2.png"))));

        int amountOfTilesWidth = SCREEN_WIDTH / UNIT_SIZE;
        int amountOfTilesHight = SCREEN_HEIGHT / UNIT_SIZE;

        for (int row = 0; row < amountOfTilesHight; row++) {
            for (int col = 0; col < amountOfTilesWidth; col++) {

                //create imageview from list of images, randomly
                ImageView tileView = new ImageView(tileImages.get(random.nextInt(tileImages.size())));

                // Create and configure FloorTile
                FloorTile floorTile = new FloorTile(tileView);

                //position
                tileView.setX(col * GameMap.UNIT_SIZE);
                tileView.setY(row * GameMap.UNIT_SIZE);

                //add to scene
                this.getChildren().add(floorTile.getImage());
            }
        }
    }

    /**
     * starts the game and initializes necessary participants and starts timeline
     */
    public void startGame(Stage stage, Game game) {
        isRunning = true;
        draw();
        spawnSnake(stage, game);
        spawnFood(fruitsToSpawn);
        initScore();
        initGameSpeed();
    }

    /**
     * spawns the snake
     */
    private void spawnSnake(Stage stage, Game game) {
        snakeHead = new SnakeHead(Direction.Left, 3,UNIT_SIZE*10,UNIT_SIZE*10);
        this.getChildren().add(snakeHead.getNode());
        snakeHead.parent = this;
        snakeHead.setOnDeath(()->{
            Platform.runLater(()->{
                GameOver gameover = new GameOver(stage, game,score);
                this.getChildren().add(gameover);

            });
        });

    }

    /**
     * Handles the ticks of the game loop
     */
    public void update()
    {
        if (snakeHead.getLengthOfSnake() <= 0 )
        {
            System.exit(0);
        }
        snakeHead.tick();
        snakeHead.move();
        checkIfSnakeIsOnTopOfFoodAndIsHellaHungry();
        score.tick(snakeHead);
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
            else if (chance <= 95) {
                food = new Weed(
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
                if (food.getClass() == Brick.class){
                    if ( amountOfBricks <= 1){
                        amountOfBricks++;
                        this.getChildren().add(food.getImage());
                        foods.add(food);
                    }
                    spawnFood(1);
                }
                else {
                    this.getChildren().add(food.getImage());
                    foods.add(food);
                }

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
    public void initGameSpeed() {
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
                    gameTime.changeToModifier(0.2f);
                }
                if (foods.get(i).getClass() == Brick.class) {
                    amountOfBricks--;
                    getChildren().remove(foods.get(i).getImage());
                    foods.remove(foods.get(i));
                    snakeHead.addToLengthOfSnake(-3);
                    spawnFood(1);

                    // replaces gamespeed
                    gameTime.changeToModifier(-0.2f);
                }

                if (foods.get(i).getClass() == Weed.class) {
                    getChildren().remove(foods.get(i).getImage());
                    foods.remove(foods.get(i));
                    spawnFood(1);

                    // reset game speed
                    gameTime.resetBaseSpeedToOriginal();

                    // Rotate the canvas
                    canvas.setRotate((canvas.getRotate() + 90) % 360);

                    // Reposition the canvas to keep it centered
                    canvas.setTranslateX((SCREEN_WIDTH - canvas.getHeight()) / 2);
                    canvas.setTranslateY((SCREEN_HEIGHT - canvas.getWidth()) / 2);

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
