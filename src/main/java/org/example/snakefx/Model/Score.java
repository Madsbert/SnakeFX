package org.example.snakefx.Model;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Score {
    private int score;
    private final Text SCORE_TEXT = new Text();

    public Score(int score) {

        this.score = score;
        SCORE_TEXT.setText("Score: " + score);
        SCORE_TEXT.setFont(Font.font("Comic Sans MS", 20));
        SCORE_TEXT.setX(20);
        SCORE_TEXT.setY(20);
    }
    public void addToScore(){
        score += 1;
    }

    public Text getNode(){
        return this.SCORE_TEXT;
    }
    public void showScore() {}
}

