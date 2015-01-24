package svanimpe.pong.objects;

public class Ball extends GameObject
{
    /* --- Construction and final properties --- */
    
    private final double maxSpeed;

    public Ball(double maxSpeed)
    {
        this.maxSpeed = maxSpeed;
    }

    public double getMaxSpeed()
    {
        return maxSpeed;
    }
    
    /* --- Angle (in radians) --- */
    
    private double angle = 0;

    public double getAngle()
    {
        return angle;
    }

    public void setAngle(double angle)
    {
        this.angle = angle;
    }
    
    /* --- Speed --- */
    
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
    
    /* --- Update --- */
    
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
