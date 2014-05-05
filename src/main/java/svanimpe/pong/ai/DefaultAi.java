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

package svanimpe.pong.ai;

import svanimpe.pong.Game;
import svanimpe.pong.objects.Paddle;

import static svanimpe.pong.Constants.*;

public class DefaultAi extends PaddleAi
{
    /* --- Construction and final properties --- */
    
    private static final double timeBetweenUpdates = 0.2;
    
    public DefaultAi(Paddle paddle, Game game)
    {
        super(paddle, game);
    }

    /* --- Update --- */
    
    private double timeSinceLastUpdate = 0;
    
    @Override
    public void update(double deltaTime)
    {
        timeSinceLastUpdate += deltaTime;
        
        if (timeSinceLastUpdate < timeBetweenUpdates) {
            return; /* Wait a while longer. */
        }
        
        timeSinceLastUpdate -= timeBetweenUpdates;
        
        double distanceFromPaddle = getPaddle().getX() - getGame().getBall().getX();
        
        /*
         * Do nothing if the ball is not moving towards us.
         */
        if (Math.signum(distanceFromPaddle) != Math.signum(getGame().getBall().getSpeed())) {
            getPaddle().setMovement(Paddle.Movement.NONE);
            return;
        }

        /*
         * Find out where the ball is heading for and move in that direction (this does not look
         * ahead past collisions).
         */
        double targetY = getGame().getBall().getY() + distanceFromPaddle * Math.tan(getGame().getBall().getAngle());
        boolean paddleOnTarget = targetY >= getPaddle().getY() && targetY + BALL_SIZE <= getPaddle().getY() + PADDLE_HEIGHT;
        if (paddleOnTarget) {
            getPaddle().setMovement(Paddle.Movement.NONE);
        } else if (targetY < getPaddle().getY()) {
            getPaddle().setMovement(Paddle.Movement.UP);
        } else if (targetY > getPaddle().getY()) {
            getPaddle().setMovement(Paddle.Movement.DOWN);
        }
    }
}
