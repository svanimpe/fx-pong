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

package svanimpe.pong.objects;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class Paddle extends GameObject
{
    /* --- Construction and final properties --- */
    
    private final double speed;

    public Paddle(double speed)
    {
        this.speed = speed;
    }

    public double getSpeed()
    {
        return speed;
    }
    
    /* --- Movement (as seen by the user, meaning UP refers to the negative y-direction) --- */
    
    public enum Movement
    {
       UP, DOWN, NONE;
    }
    
    private Movement movement = Movement.NONE;

    public Movement getMovement()
    {
        return movement;
    }

    public void setMovement(Movement movement)
    {
        this.movement = movement;
    }
    
    /* --- Score --- */
    
    private final IntegerProperty score = new SimpleIntegerProperty(0);
    
    public int getScore()
    {
        return score.get();
    }
    
    public void setScore(int score)
    {
        this.score.set(score);
    }
    
    public IntegerProperty scoreProperty()
    {
        return score;
    }
    
    /* --- Update --- */
    
    @Override
    public void update(double deltaTime)
    {
        if (movement == Movement.DOWN) {
            setY(getY() + speed * deltaTime);
        } else if (movement == Movement.UP) {
            setY(getY() - speed * deltaTime);
        }
    }
}
