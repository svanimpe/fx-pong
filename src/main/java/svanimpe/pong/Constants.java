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

import static svanimpe.pong.Utilities.degreesToRadians;
 
public class Constants
{   
    private Constants() {}
    
    /* Size and layout */
    
    public static final double WIDTH = 568;
    public static final double HEIGHT = 320;
    public static final double MARGIN_TOP_BOTTOM = 10;
    public static final double MARGIN_LEFT_RIGHT = 10;
    public static final double GOAL_WIDTH = 20;
    
    public static final double TEXT_INSETS_TOP_BOTTOM = 20;
    public static final double SCORE_SPACING = 80;

    /* Ball */
    
    public static final double BALL_SIZE = 6;
    public static final double BALL_INITIAL_SPEED = -200; /* Negative so towards the player by default */
    public static final double BALL_SPEED_INCREASE = -1.25; /* Negative because the direction of the ball changes as well */
    public static final double BALL_MAX_SPEED = 600;

    /* Paddle */
    
    public static final double PLAYER_PADDLE_SPEED = 300;
    public static final double OPPONENT_PADDLE_SPEED = 250;
    
    public static final int PADDLE_SECTIONS = 8;
    public static final double[] PADDLE_SECTION_ANGLES = new double[] {degreesToRadians(-60),
                                                                       degreesToRadians(-40),
                                                                       degreesToRadians(-20),
                                                                       degreesToRadians(0),
                                                                       degreesToRadians(0),
                                                                       degreesToRadians(20),
                                                                       degreesToRadians(40),
                                                                       degreesToRadians(60)};
    public static final double PADDLE_WIDTH = 5;
    public static final double PADDLE_HEIGHT = PADDLE_SECTIONS * BALL_SIZE;
    
    /* Game */
    
    public static final int MAX_SCORE = 10;   
}
