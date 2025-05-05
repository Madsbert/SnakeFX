package org.example.snakefx.Model;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameTime {
    private float time = 0.3f;
    private final Text TIME_TEXT = new Text();

    public GameTime() {
        TIME_TEXT.setText("GameTime: " + time);
        TIME_TEXT.setFont(Font.font("Comic Sans MS", 20));
        TIME_TEXT.setX(20);
        TIME_TEXT.setY(40);
    }

    public void tick()
    {
        TIME_TEXT.setText("GameTime: " + time);
    }

    public Text getNode(){
        return this.TIME_TEXT;
    }
    public void showScore() {}
}

