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

package svanimpe.pong;

import java.util.Random;
import javafx.animation.AnimationTimer;
import javafx.scene.media.AudioClip;
import svanimpe.pong.ai.DefaultAi;
import svanimpe.pong.ai.PaddleAi;
import svanimpe.pong.objects.Ball;
import svanimpe.pong.objects.Paddle;

import static svanimpe.pong.Constants.*;

public class Game
{
    /* Construction and final properties */
    
    private final Random random = new Random();
    
    private final int maxScore;
    
    public Game(int maxScore)
    {
        this.maxScore = maxScore;
        loop.start();
    }
    
    /* Game loop */
    
    private class GameLoop extends AnimationTimer
    {
        private long previousTime = 0;

        @Override
        public void handle(long currentTime)
        {
            if (previousTime == 0) {
                previousTime = currentTime; /* Not much else to do at the start of the first frame */
                return;
            }

            double secondsElapsed = (currentTime - previousTime) / 1_000_000_000.0; /* Convert nanoseconds to seconds */

            if (secondsElapsed > 0.05) {
                secondsElapsed = 0.05; /* Avoid large time steps by imposing an upper bound */
            }
            
            updateGame(secondsElapsed);
            
            previousTime = currentTime;
        }
    }
    
    private final GameLoop loop = new GameLoop();
    
    /* State */
    
    public enum State
    {
        PLAYING, PAUSED, ENDED;
    }
    
    private State state = State.ENDED;
    
    public State getState()
    {
        return state;
    }

    private Runnable onGameEnd = () -> {}; /* Do nothing for now */
    
    public void setOnGameEnd(Runnable onGameEnd)
    {
        this.onGameEnd = onGameEnd;
    }
    
    public void start()
    {
        player.setX(MARGIN_LEFT_RIGHT + GOAL_WIDTH - PADDLE_WIDTH);
        player.setY((HEIGHT - PADDLE_HEIGHT) / 2);
        
        opponent.setX(WIDTH - MARGIN_LEFT_RIGHT - GOAL_WIDTH);
        opponent.setY((HEIGHT - PADDLE_HEIGHT) / 2);
        
        player.setScore(0);
        opponent.setScore(0);
        
        player.setMovement(Paddle.Movement.NONE);
        opponent.setMovement(Paddle.Movement.NONE);
        
        launchBall();
        
        state = State.PLAYING;
    }

    public void pause()
    {
        if (state == State.PAUSED) {
            state = State.PLAYING;
        } else if (state == State.PLAYING) {
            state = State.PAUSED;
        }
    }

    public void forfeit()
    {
        player.setScore(0);
        opponent.setScore(maxScore);
        state = State.ENDED;
        onGameEnd.run();
    }
    
    /* Ball */
    
    private final Ball ball = new Ball(BALL_MAX_SPEED);

    public Ball getBall()
    {
        return ball;
    }
    
    public void launchBall()
    {
        boolean towardsOpponent = random.nextBoolean();
        double initialAngle = PADDLE_SECTION_ANGLES[random.nextInt(2) + 1]; /* We don't use the steepest angle */
        
        ball.setSpeed(towardsOpponent ? -BALL_INITIAL_SPEED : BALL_INITIAL_SPEED);
        ball.setAngle(towardsOpponent ? -initialAngle : initialAngle);
        ball.setX((WIDTH - BALL_SIZE) / 2);
        ball.setY(MARGIN_TOP_BOTTOM);
    }
    
    /* Player */
    
    private final Paddle player = new Paddle(PLAYER_PADDLE_SPEED);
    
    public Paddle getPlayer()
    {
        return player;
    }
    
    /* Opponent */
    
    private final Paddle opponent = new Paddle(OPPONENT_PADDLE_SPEED);
    
    private final PaddleAi ai = new DefaultAi(opponent, this);
    
    public Paddle getOpponent()
    {
        return opponent;
    }
    
    /* Update */
    
    private void updateGame(double deltaTime)
    {
        if (state == State.PAUSED || state == State.ENDED) {
            return; /* This is necessary because the loop keeps running even when the game is paused or stopped */
        }
        
        player.update(deltaTime);
        opponent.update(deltaTime);
        
        keepPaddleInBounds(player);
        keepPaddleInBounds(opponent);
        
        ball.update(deltaTime);
        
        checkWallCollision();
        checkPaddleOrEdgeCollision(player);
        checkPaddleOrEdgeCollision(opponent);
        
        ai.update(deltaTime);
    }
    
    /* Collision detection */
    
    private void keepPaddleInBounds(Paddle paddle)
    {
        if (paddle.getY() < MARGIN_TOP_BOTTOM) {
            paddle.setY(MARGIN_TOP_BOTTOM);
        } else if (paddle.getY() + PADDLE_HEIGHT > HEIGHT - MARGIN_TOP_BOTTOM) {
            paddle.setY(HEIGHT - MARGIN_TOP_BOTTOM - PADDLE_HEIGHT);
        }
    }
    
    private void checkWallCollision()
    {
        boolean ballHitTopWall = ball.getY() < MARGIN_TOP_BOTTOM;
        boolean ballHitBottomWall = ball.getY() + BALL_SIZE > HEIGHT - MARGIN_TOP_BOTTOM;
        
        if (ballHitTopWall || ballHitBottomWall) {
            ball.setAngle(ball.getAngle() * -1);
            new AudioClip(Sounds.HIT_WALL).play();
        }
        
        if (ballHitTopWall) {
            ball.setY(MARGIN_TOP_BOTTOM);
        } else if (ballHitBottomWall) {
            ball.setY(HEIGHT - MARGIN_TOP_BOTTOM - BALL_SIZE);
        }
    }
    
    private void checkPaddleOrEdgeCollision(Paddle paddle)
    {
        boolean ballHitEdge;
        if (paddle == player) {
            ballHitEdge = ball.getX() < MARGIN_LEFT_RIGHT + GOAL_WIDTH;
        } else {
            ballHitEdge = ball.getX() + BALL_SIZE > WIDTH - MARGIN_LEFT_RIGHT - GOAL_WIDTH;
        }
        
        if (!ballHitEdge) {
            return;
        }
        
        boolean ballHitPaddle = ball.getY() + BALL_SIZE > paddle.getY() && ball.getY() < paddle.getY() + PADDLE_HEIGHT;
        
        if (ballHitPaddle) {
            
            /* Find out what section of the paddle was hit */
            
            for (int i = 0; i < PADDLE_SECTIONS; i++) {
                boolean ballHitCurrentSection = ball.getY() < paddle.getY() + (i + 0.5) * BALL_SIZE;
                if (ballHitCurrentSection) {
                    ball.setAngle(PADDLE_SECTION_ANGLES[i] * (paddle == opponent ? -1 : 1));
                    break; /* Found our match */
                } else if (i == PADDLE_SECTIONS - 1) { /* Must be the last one then */
                    ball.setAngle(PADDLE_SECTION_ANGLES[i] * (paddle == opponent ? -1 : 1));
                }
            }
            
            /* Update speed */
            
            ball.setSpeed(ball.getSpeed() * BALL_SPEED_INCREASE);
            
            /* Reposition ball */
            
            if (paddle == player) {
                ball.setX(MARGIN_LEFT_RIGHT + GOAL_WIDTH);
            } else {
                ball.setX(WIDTH - MARGIN_LEFT_RIGHT - GOAL_WIDTH - BALL_SIZE);
            }
                        
            new AudioClip(Sounds.HIT_PADDLE).play();
            
        } else {
            
            /* Update score */
            
            if (paddle == opponent) {
                player.setScore(player.getScore() + 1);
                new AudioClip(Sounds.SCORE_PLAYER).play();
            } else {
                opponent.setScore(opponent.getScore() + 1);
                new AudioClip(Sounds.SCORE_OPPONENT).play();
            }
            
            /* Check for game end */
            
            if (player.getScore() == maxScore || opponent.getScore() == maxScore) {
                state = State.ENDED;
                onGameEnd.run();
            } else {
                launchBall();
            }
        }
    }
}
