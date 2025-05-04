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
import org.example.snakefx.Model.SnakeHead;

public class GameMap extends Pane {
    static final int SCREEN_WIDTH = 800;
    static final int SCREEN_HEIGHT = 800;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * UNIT_SIZE) / UNIT_SIZE;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    boolean isRunning = false;
    private SnakeHead snakeHead;

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
        draw();
        spawnFood();
        spawnSnake();
    }

    private void spawnSnake() {
        SnakeHead snakeHead = new SnakeHead(Direction.Left, 1,250,250,Color.RED);
        this.getChildren().add(snakeHead.getNode());
        isRunning = true;

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        snakeHead.move(GameMap.UNIT_SIZE);

                    }
                }));
        timeline.play();
    }

    private void spawnFood() {
        // Implementation for spawning food
    }
    public void setSnakeDirection(Direction direction) {
        if (snakeHead != null) {
            snakeHead.setDirection(direction);
        }
    }
}