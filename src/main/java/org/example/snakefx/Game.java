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
import org.example.snakefx.Controller.MainMenu;
import org.example.snakefx.Model.Direction;

/**
 * class to take input and launch the game
 */
public class Game extends Application {
private GameMap gameMap;
    /**
     * launches the game
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

    /**
     * sets the scene
     * @param primaryStage
     */
    @Override
    public void start(Stage primaryStage) {
        MainMenu mainMenu = new MainMenu(primaryStage, this);
        Scene scene = new Scene(mainMenu, 800, 800);
        primaryStage.setTitle("Snake Menu");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * starts the game and takes keypress input from user
     * @param primaryStage a stage
     */
    public void startGame(Stage primaryStage) {
        this.gameMap = new GameMap();
            Scene gameScene = new Scene(gameMap);
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
            primaryStage.setScene(gameScene);
            primaryStage.setTitle("Snake");
            primaryStage.show();

            gameMap.requestFocus();
            gameMap.startGame(primaryStage, this);

            gameScene.setOnKeyPressed(this::handleKeyPress);

    }

    /**
     * input handler
     * @param event key presses from user
     */
    private void handleKeyPress(KeyEvent event) {
        if (gameMap.freeToMove) {
            switch (event.getCode()) {
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
