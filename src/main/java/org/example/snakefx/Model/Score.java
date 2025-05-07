package org.example.snakefx.Model;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.example.snakefx.Controller.GameMap;

/**
 * class to have a score
 */
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

    /**
     * gets the score and sets the text
     * @param snakeHead
     */
    public void tick(SnakeHead snakeHead)
    {
        score = snakeHead.lengthOfSnake - 3;
        SCORE_TEXT.setText("Score: " + score);
    }

    /**
     * method to get the Textfield
     * @return a textfield
     */
    public Text getNode(){
        return this.SCORE_TEXT;
    }

    /**
     * returns the score
     * @return score
     */
    public int getScore() {
        return score;
    }

}

