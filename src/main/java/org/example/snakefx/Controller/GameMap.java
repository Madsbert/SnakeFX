package org.example.snakefx.Controller;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.example.snakefx.Game;
import org.example.snakefx.Model.*;
import org.example.snakefx.Model.Foods.*;

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
    private static SnakeHead snakeHead;
    private List<Food> foods = new ArrayList<>();
    int fruitsToSpawn = 10;
    int amountOfBricks = 0;
    int weedPlantsEaten = 0;
    int ticks;
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

    public static SnakeHead getSnakeHead() {
        return snakeHead;
    }

    /**
     * draws FloorTiles
     */
    public void draw() {
        List<Image> tileImages = new ArrayList<>();

        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile1.png.png"))));
        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile2.png.png"))));
        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile3.png.png"))));
        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile4.png.png"))));
        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile5.png.png"))));
        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile6.png.png"))));
        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile7.png.png"))));
        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile8.png.png"))));
        tileImages.add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Pictures/FloorTiles/DirtTile9.png.png"))));

        int amountOfTilesWidth = SCREEN_WIDTH / UNIT_SIZE;
        int amountOfTilesHeight = SCREEN_HEIGHT / UNIT_SIZE;

        for (int row = 0; row < amountOfTilesHeight; row++) {
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
        initScore();
        spawnSnake(stage, game);
        spawnFood(fruitsToSpawn);
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

                this.setRotate(0);
            });
        });

    }

    /**
     * Handles the ticks of the game loop
     */
    public void update()
    {
        if (snakeHead.getLengthOfSnake() <= 2 )
        {
            snakeHead.die();
            gameTime.setModifier(0);
        }
        snakeHead.tick();
        snakeHead.move();
        checkIfSnakeIsOnTopOfFoodAndIsHellaHungry();
        score.tick(snakeHead);
        freeToMove = true;

        for (int i = 0; i < foods.size(); i++) {
            foods.get(i).tick();
            if (foods.get(i).lifetime <= 0)
            {
                if (foods.get(i).getClass() == Brick.class)
                {
                    amountOfBricks--;
                }
                getChildren().remove(foods.get(i).getImage());
                foods.remove(i);
                spawnFood(1);
                i--;
            }
        }

        gameTime.tick();

        Runtime.getRuntime().gc();

        if (weedPlantsEaten == 1){
            ticks++;
            if (ticks == 30){
                this.setRotate((this.getRotate() - 90) % 360);
                ticks = 0;
                weedPlantsEaten = 0;
            }
        }
    }

    /**
     *Spawns the food on the map
     * @param fruitsToSpawn how many fruits that should be on the map
     */
    private void spawnFood(int fruitsToSpawn)
    {


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
            else if (chance <= 98) {
                food = new Dragonfruit(
                        random.nextInt(Math.round((float) SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE,
                        random.nextInt(Math.round((float) SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE);
            }
            else {
                if (score.getScore() <= 25)
                {
                    food = new Weed(
                            random.nextInt(Math.round((float) SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE,
                            random.nextInt(Math.round((float) SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE);
                }
                else
                {
                    spawnFood(1);
                    return;
                }

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
        score = new Score();
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
        for (int i = 0; i < foods.size(); i++) {

            if (snakeHead.rect.contains(new Point2D(foods.get(i).getPositionX() - (double) GAME_UNITS /2,
                    foods.get(i).getPositionY() - (double) GAME_UNITS /2)) || snakeHead.rect.getBoundsInParent().contains(foods.get(i).rect.getBoundsInParent()))
            {

                if (foods.get(i).getClass() == Dragonfruit.class) {

                    getChildren().remove(foods.get(i).getImage());
                    foods.remove(foods.get(i));
                    snakeHead.addToLengthOfSnake(3);
                    snakeHead.increaseSnakeSize(0.25f);
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
                    weedPlantsEaten++;
                    getChildren().remove(foods.get(i).getImage());
                    foods.remove(foods.get(i));
                    spawnFood(1);

                    // reset game speed
                    gameTime.resetBaseSpeedToOriginal();

                    // Rotate the canvas
                    this.setRotate((this.getRotate() + 90) % 360);

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
