package svanimpe.pong.ui;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.EventHandler;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import static svanimpe.pong.Constants.*;

public class EndScreen extends Pane
{
    private Runnable onRestart = new Runnable()
    {
        @Override
        public void run() { } /* Do nothing for now. */
    };
    
    public void setOnRestart(Runnable onRestart)
    {
        this.onRestart = onRestart;
    }
    
    private final Text header = new Text();
    
    public void setScore(int playerScore)
    {
        header.setText(playerScore == WINNING_SCORE ? "you win" : "you lose");
    }
    
    public EndScreen()
    {
        header.boundsInLocalProperty().addListener(new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
            {
                /*
                 * When using CSS, the width and height (with CSS applied) aren't available right
                 * away. Therefore, we listen for changes and update the position once the width and
                 * height are available.
                 */
                header.setTranslateX((WIDTH - header.getBoundsInLocal().getWidth()) / 2); /* Centered. */
                header.setTranslateY(TEXT_MARGIN_TOP_BOTTOM);
            }
        });
        header.getStyleClass().add("header");
        
        final Text info = new Text("tap to restart");
        info.boundsInLocalProperty().addListener(new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
            {
                /*
                 * When using CSS, the width and height (with CSS applied) aren't available right
                 * away. Therefore, we listen for changes and update the position once the width and
                 * height are available.
                 */
                info.setTranslateX((WIDTH - info.getBoundsInLocal().getWidth()) / 2); /* Centered. */
                info.setTranslateY(HEIGHT - TEXT_MARGIN_TOP_BOTTOM - info.getBoundsInLocal().getHeight());
            }
        });
        info.getStyleClass().add("info");
        
        setPrefSize(WIDTH, HEIGHT);
        getChildren().addAll(header, info);
        getStyleClass().add("screen");
        
        setOnTouchPressed(new EventHandler<TouchEvent>()
        {
            @Override
            public void handle(TouchEvent event)
            {
                onRestart.run();
            }
        });
    }
}