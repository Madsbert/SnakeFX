package org.example.snakefx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import org.example.snakefx.Controller.GameMap;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import org.example.snakefx.Model.Direction;

public class Game extends Application {
    private GameMap gameMap;

    public static void main(String[] args) {
        launch();
    }


    @Override
    public void start(Stage primaryStage) {
        gameMap = new GameMap();

        Scene scene = new Scene(gameMap);

        // Set up key event handling
        scene.setOnKeyPressed(this::handleKeyPress);

        primaryStage.setTitle("Snake");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.centerOnScreen();
        primaryStage.show();
        gameMap.startGame();
    }

    private void handleKeyPress(KeyEvent event) {
        if (gameMap.freeToMove)
        {
            switch(event.getCode()) {
                case LEFT:
                    gameMap.setSnakeDirection(Direction.Left);
                    break;
                case RIGHT:
                    gameMap.setSnakeDirection(Direction.Right);
                    break;
                case UP:
                    gameMap.setSnakeDirection(Direction.Up);
                    break;
                case DOWN:
                    gameMap.setSnakeDirection(Direction.Down);
                    break;
            }

            gameMap.freeToMove = false;
        }
    }

}
