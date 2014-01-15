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
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import svanimpe.pong.Game;

import static svanimpe.pong.Constants.*;

public class Pong extends Application
{    
    @Override
    public void start(Stage stage)
    {
        Font.loadFont(getClass().getResourceAsStream("/arcade-normal.ttf"), 0); /* Arcade Normal */
        
        Game game = new Game(MAX_SCORE);
        
        GameScreen gameScreen = new GameScreen(game);
        WelcomeScreen welcomeScreen = new WelcomeScreen();
        EndScreen endScreen = new EndScreen();
        
        Scene scene = new Scene(welcomeScreen);
        scene.getStylesheets().add("/styles.css");
        
        welcomeScreen.setOnStart(() -> 
        {
            scene.setRoot(gameScreen);
            gameScreen.requestFocus();
            game.start();
        });
        
        game.setOnGameEnd(() ->
        {
            scene.setRoot(endScreen);
            endScreen.requestFocus();
            endScreen.setScore(game.getPlayer().getScore());
        });
        
        endScreen.setOnRestart(() ->
        {
            scene.setRoot(gameScreen);
            gameScreen.requestFocus();
            game.start();
        });
        
        stage.setScene(scene);
        stage.setTitle("Pong");
        stage.setResizable(false);
        stage.show();
        
        welcomeScreen.requestFocus(); /* This step is necessary to receive keyboard input */
    }
    
    public static void main(String... args)
    {
        Application.launch(Pong.class, args);
    }
}
