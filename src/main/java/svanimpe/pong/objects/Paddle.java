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
