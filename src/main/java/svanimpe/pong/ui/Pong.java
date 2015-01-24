package svanimpe.pong.ui;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import svanimpe.pong.Game;

import static svanimpe.pong.Constants.*;

public class Pong extends Application
{    
    @Override
    public void start(Stage stage)
    {
        Font.loadFont(getClass().getResource("/arcade-normal.ttf").toExternalForm(), 0); /* Font family 'Arcade Normal' in CSS. */
        
        Game game = new Game(WINNING_SCORE);
        
        Group content = new Group();
        GameScreen gameScreen = new GameScreen(game);
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        EndScreen endScreen = new EndScreen();
        content.getChildren().add(welcomeScreen);
        
        /*
         * Screen transitions.
         */
        welcomeScreen.setOnStart(() -> 
        {
            content.getChildren().clear();
            content.getChildren().add(gameScreen);
            gameScreen.requestFocus();
            game.start();
        });
        game.setOnGameEnd(() ->
        {
            content.getChildren().clear();
            content.getChildren().add(endScreen);
            endScreen.requestFocus();
            endScreen.setScore(game.getPlayer().getScore());
        });
        endScreen.setOnRestart(() ->
        {
            content.getChildren().clear();
            content.getChildren().add(gameScreen);
            gameScreen.requestFocus();
            game.start();
        });
        
        /*
         * The content is wrapped in a Group so it can be scaled (the root node itself cannot be
         * scaled as it scales with the scene).
         */
        Scene scene = new Scene(new Group(content), WIDTH, HEIGHT, Color.BLACK);
        scene.getStylesheets().add("/styles.css");
        
        Scale scale = Transform.scale(1, 1, 0, 0);
        content.getTransforms().add(scale);

        /*
         * The following listener is called whenever the scene is resized to update the scale and
         * add letter- and pillarboxing.
         */
        InvalidationListener updateScale = value ->
        {
            double scaleX = scene.getWidth() / WIDTH;
            double scaleY = scene.getHeight() / HEIGHT;

            if (scaleX < scaleY) {
                /*
                 * Letterboxing.
                 */
                scale.setX(scaleX);
                scale.setY(scaleX);
                double remainingHeight = scene.getHeight() - HEIGHT * scaleX;
                content.setTranslateX(0);
                content.setTranslateY(remainingHeight / 2);
            } else if (scaleY < scaleX) {
                /*
                 * Pillarboxing.
                 */
                scale.setX(scaleY);
                scale.setY(scaleY);
                double remainingWidth = scene.getWidth() - WIDTH * scaleY;
                content.setTranslateX(remainingWidth / 2);
                content.setTranslateY(0);
            } else {
                /*
                 * Regular scaling.
                 */
                scale.setX(scaleX);
                scale.setY(scaleY);
                content.setTranslateX(0);
                content.setTranslateY(0);
            }
        };
        scene.widthProperty().addListener(updateScale);
        scene.heightProperty().addListener(updateScale);
        
        stage.setScene(scene);
        stage.setTitle("Pong");
        stage.show();
        
        welcomeScreen.requestFocus(); /* This step is necessary to receive keyboard input. */
    }
    
    public static void main(String... args)
    {
        Application.launch(Pong.class, args);
    }
}
