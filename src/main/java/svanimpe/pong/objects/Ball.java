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

public class Ball extends GameObject
{
    /* Construction and final properties */
    
    private final double maxSpeed;

    public Ball(double maxSpeed)
    {
        this.maxSpeed = maxSpeed;
    }
    
    /* Angle (in radians) */
    
    private double angle = 0;

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }
    
    /* Speed */
    
    private double speed = 0;

    public double getSpeed()
    {
        return speed;
    }

    public void setSpeed(double speed)
    {
        if (speed >= 0) {
            this.speed = Math.min(speed, maxSpeed);
        } else {
            this.speed = Math.max(speed, -maxSpeed);
        }
    }
    
    /* Update */
    
    @Override
    public void update(double deltaTime)
    {
        double distanceTravelled = speed * deltaTime;
        double deltaX = distanceTravelled * Math.cos(angle);
        double deltaY = distanceTravelled * Math.sin(angle);
        
        setX(getX() + deltaX);
        setY(getY() + deltaY);
    }   
}
