package org.example.snakefx.Controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.Scene;
import org.example.snakefx.Game;
import org.example.snakefx.Model.Direction;

import java.awt.event.ActionEvent;

/**
 * class which sets up and controls the main menu window
 */
public class MainMenu extends Pane {
    public MainMenu(Stage primaryStage, Game gameInstance) {

        VBox vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(800, 800);
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom, #1e1e1e, #3a3a3a);");

        // Titel
        Label title = new Label("🐍 Snake Game");
        title.setFont(new Font("Arial Black", 48));
        title.setTextFill(Color.LIME);

        Button rulesButton = createStyledButton("📜 Regler");


        rulesButton.setOnAction(e -> {
            RulesMenu rulesMenu = new RulesMenu(primaryStage, gameInstance);
            Scene rulesScene = new Scene(rulesMenu);
            primaryStage.setResizable(false);
            primaryStage.sizeToScene();
            primaryStage.centerOnScreen();
            primaryStage.setScene(rulesScene);
            primaryStage.setTitle("Rules");
            primaryStage.show();

        });

        Button startButton = createStyledButton("▶ Start Spil");

        startButton.setOnAction(e -> gameInstance.startGame(primaryStage));



        vbox.getChildren().addAll(title,startButton, rulesButton);
        this.getChildren().addAll(vbox);
        this.setStyle("-fx-padding: 100; -fx-alignment: center;");
    }

    /**
     * method that sets up a styled button
     * @param text the text that should be in the button
     * @return a button
     */
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setFont(new Font("Arial", 24));
        button.setPrefWidth(250);
        button.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-background-radius: 10;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white; -fx-background-radius: 10;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-background-radius: 10;"));

        // Effekt (skygge)
        DropShadow shadow = new DropShadow();
        button.setEffect(shadow);

        return button;
    }

}
