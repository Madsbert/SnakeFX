package org.example.snakefx.Model;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.snakefx.Controller.MainMenu;
import org.example.snakefx.Game;

/**
 * class which shows gameover screen and has button to main menu
 */
public class GameOver extends VBox {

    private final Text GAME_OVER = new Text("GAME OVER");
    private Text playerScore= new Text();
    private final Button RETURN_MAIN_MENU = new Button("Tilbage til menu");
    private Score score;

    public GameOver(Stage primaryStage, Game gameInstance, Score score) {

        this.score = score;
        this.setAlignment(Pos.CENTER);
        this.setSpacing(40);
        this.setPrefSize(800, 800);
        this.setStyle("-fx-background-color: black;");

        playerScore.setText("Your score : " + score.getScore());
        playerScore.setFont(Font.font("Comic Sans MS", 60));
        playerScore.setStyle("-fx-fill: lime;");

        GAME_OVER.setFont(Font.font("Comic Sans MS", 72));
        GAME_OVER.setStyle("-fx-fill: lime;");


        RETURN_MAIN_MENU.setFont(Font.font("Comic Sans MS", 28));
        RETURN_MAIN_MENU.setPrefSize(300, 80);
        RETURN_MAIN_MENU.setStyle(
                "-fx-background-color: lime;" +
                        "-fx-text-fill: black;" +
                        "-fx-background-radius: 20;" +
                        "-fx-border-radius: 20;" +
                        "-fx-border-color: white;" +
                        "-fx-border-width: 2;"
        );


        RETURN_MAIN_MENU.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(primaryStage, gameInstance);
            Scene scene = new Scene(mainMenu, 800, 800);
            primaryStage.setTitle("Snake Menu");
            primaryStage.setScene(scene);
            primaryStage.show();
        });


        this.getChildren().addAll(GAME_OVER, playerScore, RETURN_MAIN_MENU);
    }
}
