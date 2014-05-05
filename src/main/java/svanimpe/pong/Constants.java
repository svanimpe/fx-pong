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

/*
 * Note: the units used in this class (and by extension the rest of the source code) are pixels
 * (size) and pixels per second (speed), unless stated otherwise. All sizes are to be understood as
 * unscaled sizes, unless stated otherwise.
 */
public class Constants
{   
    private Constants() {}
    
    /* --- Size and layout --- */
    
    /*
     * Total size of the game, including margins. This size is fixed but the game will be scaled to
     * fill the available space.
     */
    public static final double WIDTH = 568;
    public static final double HEIGHT = 320;
    
    /*
     * Size of the margins around the playing field.
     */
    public static final double MARGIN_TOP_BOTTOM = 10;
    public static final double MARGIN_LEFT_RIGHT = 10;
    
    /*
     * Size of the goal areas. These are special edge areas within the playing field. A point is
     * scored when the ball enters a goal area. The paddles are placed just inside the goal areas.
     * The inner edge of a paddle is aligned with the inner edge of the goal area it defends.
     */
    public static final double GOAL_WIDTH = 20;

    /*
     * Size of the margins around the text. These margins are independent from the margins of the
     * playing field.
     */
    public static final double TEXT_MARGIN_TOP_BOTTOM = 30;
    
    /*
     * Size of the space between the player and opponent scores.
     */
    public static final double SCORE_SPACING = 80;

    /* --- Ball --- */
    
    /*
     * Size of the ball.
     */
    public static final double BALL_SIZE = 6;
    
    /*
     * Initial speed of the ball. This speed is negative, so the ball moves towards the player by
     * default.
     */
    public static final double BALL_INITIAL_SPEED = -200;
    
    /*
     * The speed of the ball is multiplied by this factor every time the ball hits a paddle. This
     * factor is negative because the direction of the ball changes as well.
     */
    public static final double BALL_SPEED_INCREASE = -1.25;
    
    /*
     * The maximum speed of the ball, in any direction.
     */
    public static final double BALL_MAX_SPEED = 600;

    /* --- Paddle --- */
    
    /*
     * The speeds at which the paddles travel.
     */
    public static final double PLAYER_PADDLE_SPEED = 300;
    public static final double OPPONENT_PADDLE_SPEED = 250;
    
    /*
     * Size of the paddles. Note that the height of a paddle is expressed in sections. The height of
     * a section is currently the same as the size of the ball.
     */
    public static final double PADDLE_WIDTH = 5;
    public static final int PADDLE_SECTIONS = 8;
    public static final double PADDLE_SECTION_HEIGHT = BALL_SIZE; 
    public static final double PADDLE_HEIGHT = PADDLE_SECTIONS * PADDLE_SECTION_HEIGHT;
    
    /*
     * Every section of a paddle has an angle associated with it. This is the angle at which the
     * ball is reflected when the ball hits the paddle at this section. The angles given here are
     * for the player paddle. Multiply them by -1 to get the angles for the opponent paddle.
     */
    public static final double[] PADDLE_SECTION_ANGLES = new double[] {degreesToRadians(-60),
                                                                       degreesToRadians(-40),
                                                                       degreesToRadians(-20),
                                                                       degreesToRadians(0),
                                                                       degreesToRadians(0),
                                                                       degreesToRadians(20),
                                                                       degreesToRadians(40),
                                                                       degreesToRadians(60)};
    
    /* --- Game --- */
    
    /*
     * The score that signals the end of the game.
     */
    public static final int WINNING_SCORE = 10;   
}
