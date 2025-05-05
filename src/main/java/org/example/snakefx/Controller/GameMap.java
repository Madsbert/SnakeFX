package org.example.snakefx.Controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.snakefx.Model.Direction;
import org.example.snakefx.Model.GameTime;
import org.example.snakefx.Model.Score;
import org.example.snakefx.Model.Foods.Apple;
import org.example.snakefx.Model.Foods.Banana;
import org.example.snakefx.Model.Foods.Dragonfruit;
import org.example.snakefx.Model.Foods.Food;
import org.example.snakefx.Model.SnakeHead;

import java.util.List;
import java.util.Random;

public class GameMap extends Pane {
    public static final int SCREEN_WIDTH = 800;
    public static final int SCREEN_HEIGHT = 800;
    public static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * UNIT_SIZE) / UNIT_SIZE;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    boolean isRunning = false;
    private SnakeHead snakeHead;
    private Food food;
    int fruitsToSpawn = 3;
    public boolean freeToMove = true;
    private Score score;
    private GameTime gameTime;

    Timeline timeline;

    private Canvas canvas;
    private GraphicsContext gc;

    public GameMap() {
        canvas = new Canvas(SCREEN_WIDTH, SCREEN_HEIGHT);
        gc = canvas.getGraphicsContext2D();
        this.getChildren().add(canvas);

        // Set preferred size for proper layout
        this.setPrefSize(SCREEN_WIDTH, SCREEN_HEIGHT);
    }

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

    public void startGame() {
        isRunning = true;
        draw();
        spawnSnake();
        spawnFood(fruitsToSpawn);
        initScore();
        initGameSpeedText();

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.3),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        update();
                    }
                }));
        timeline.play();
    }


    private void spawnSnake() {
        snakeHead = new SnakeHead(Direction.Left, 10,250,250);
        this.getChildren().add(snakeHead.getNode());
        snakeHead.parent = this;

    }

    private void update()
    {
        snakeHead.tick();
        snakeHead.move(GameMap.UNIT_SIZE);
        freeToMove = true;

        gameTime.tick();
        System.out.println(((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024) + " KB");
        Runtime.getRuntime().gc();
    }

    private void spawnFood(int fruitsToSpawn) {
        Random random = new Random();

        // Base case: Stop recursion if no more fruits needed or game isn't running
        if (!isRunning || fruitsToSpawn <= 0) {
            return;
        }

        int chance = random.nextInt((100)+1);

          if (chance <= 67) {
              food = new Apple(
                      random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE,
                      random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE);
              this.getChildren().add(food.getImage());
          }
          else if (chance <= 90) {
              food = new Banana(
                      random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE,
                      random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE);
              this.getChildren().add(food.getImage());
          }
            else {
              food = new Dragonfruit(
                      random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE,
                      random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE);
              this.getChildren().add(food.getImage());
          }
        spawnFood(fruitsToSpawn - 1);

        }

    public void setSnakeDirection(Direction direction) {
        if (snakeHead != null) {
            snakeHead.setDirection(direction);
        }
    }

    public void initScore() {
        score = new Score(snakeHead.getLengthOfSnake());
        this.getChildren().add(score.getNode());
    }

    public void initGameSpeedText() {
        gameTime = new GameTime();
        this.getChildren().add(gameTime.getNode());
    }
}