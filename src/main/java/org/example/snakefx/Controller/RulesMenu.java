package org.example.snakefx.Controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import org.example.snakefx.Game;

public class RulesMenu extends StackPane {

    public RulesMenu(Stage primaryStage, Game gameInstance) {
        VBox vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPrefSize(800, 800);
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom, #202020, #3a3a3a);");

        Label rulesLabel = new Label("📜 Spillets Regler:\n\n- Brug piletasterne til at styre slangen.\n- Undgå væggen og dig selv.\n- Spis maden for at vokse.\n\nHeld og lykke!");
        rulesLabel.setFont(new Font("Arial", 20));
        rulesLabel.setStyle("-fx-text-fill: white; -fx-alignment: center; -fx-padding: 20;");

        Button backButton = new Button("⬅️ Tilbage");
        styleButton(backButton);
        backButton.setOnAction(e -> {
            MainMenu mainMenu = new MainMenu(primaryStage, gameInstance );
            Scene menuScene = new Scene(mainMenu, 800, 800);
            primaryStage.setScene(menuScene);
        });

        vbox.getChildren().addAll(rulesLabel, backButton);
        this.getChildren().add(vbox);
        this.setStyle("-fx-background-color: #111111;");
    }

    private void styleButton(Button button) {
        button.setFont(new Font("Arial", 20));
        button.setStyle(
                "-fx-background-color: #5BC0BE;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 30;" +
                        "-fx-cursor: hand;"
        );

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #3A506B;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 30;" +
                        "-fx-cursor: hand;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #5BC0BE;" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;" +
                        "-fx-padding: 10 30;" +
                        "-fx-cursor: hand;"
        ));
    }
}