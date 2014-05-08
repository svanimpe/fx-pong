/*
 * Copyright (c) 2014, Steven Van Impe
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *  1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the
 *     following disclaimer in the documentation and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package svanimpe.pong.ui;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
        Font.loadFont(getClass().getResourceAsStream("/arcade-normal.ttf"), 0); /* Font family 'Arcade Normal' in CSS. */
        
        final Game game = new Game(WINNING_SCORE);
        
        final Group content = new Group();
        final GameScreen gameScreen = new GameScreen(game);
        final WelcomeScreen welcomeScreen = new WelcomeScreen();
        final EndScreen endScreen = new EndScreen();
        content.getChildren().add(welcomeScreen);
        
        /*
         * Screen transitions.
         */
        welcomeScreen.setOnStart(new Runnable()
        {
            @Override
            public void run()
            {
                content.getChildren().clear();
                content.getChildren().add(gameScreen);
                gameScreen.requestFocus();
                game.start();
            }
        });
        game.setOnGameEnd(new Runnable()
        {
            @Override
            public void run()
            {
                content.getChildren().clear();
                content.getChildren().add(endScreen);
                endScreen.requestFocus();
                endScreen.setScore(game.getPlayer().getScore());
            }
        });
        endScreen.setOnRestart(new Runnable()
        {
            @Override
            public void run()
            {
                content.getChildren().clear();
                content.getChildren().add(gameScreen);
                gameScreen.requestFocus();
                game.start();
            }
        });
        
        /*
         * The content is wrapped in a Group so it can be scaled (the root node itself cannot be
         * scaled as it scales with the scene).
         */
        final Scene scene = new Scene(new Group(content), WIDTH, HEIGHT, Color.BLACK);
        scene.getStylesheets().add("/styles.css");
        
        final Scale scale = Transform.scale(1, 1, 0, 0);
        content.getTransforms().add(scale);

        /*
         * The following listener is called whenever the scene is resized to update the scale and
         * add letter- and pillarboxing.
         */
        InvalidationListener updateScale = new InvalidationListener()
        {
            @Override
            public void invalidated(Observable observable)
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
