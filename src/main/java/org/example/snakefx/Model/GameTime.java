package org.example.snakefx.Model;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.snakefx.Controller.GameMap;
import org.example.snakefx.Game;

/**
 *class which shows the user how fast the game is moving
 */
public class GameTime {
    private Timeline timeline;
    private GameMap gameMap;

    private final float BASETIME = 0.2f;
    private float timeModifier = 1f; // Procent
    private final Text TIME_TEXT = new Text();

    public GameTime(GameMap gameMap) {
        TIME_TEXT.setText("GameTime: ");
        TIME_TEXT.setFont(Font.font("Comic Sans MS", 20));
        TIME_TEXT.setX(20);
        TIME_TEXT.setY(40);
        this.gameMap = gameMap;

        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(false);

        updateTime();
    }

    /**
     * gets the current gametime
     */
    private void updateTime() {
        timeline.stop();
        timeline.getKeyFrames().clear();
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(BASETIME * timeModifier), e -> {gameMap.update();});
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    /**
     * changes the modifier with subtraction.
     * @param modifier 0.5 = 50% faster than base speed/time.
     */
    public void changeToModifier(float modifier) {
        if (timeModifier - modifier < 0.2f || timeModifier - modifier > 1.8f) { return; }
        timeModifier -= modifier;
        updateTime();
    }

    public void resetBaseSpeedToOriginal(){
        timeModifier = 1;
        updateTime();
    }

    /**
     * set the time modifier
     * @param modifier the time modifier
     */
    public void setModifier(float modifier) {
        timeModifier = modifier;
        updateTime();
    }

    /**
     * sets the gametime in game
     */
    public void tick()
    {
        float mod = BASETIME + (2 - BASETIME) - (BASETIME * timeModifier) / BASETIME;
        TIME_TEXT.setText("GameTime: " + Math.round(mod * 100) + "%");
    }

    /**
     * method to get the textfield
     * @return a textfield
     */
    public Text getNode(){
        return this.TIME_TEXT;
    }

}

