package svanimpe.pong.objects;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class GameObject
{
    /* --- Position --- */
    
    private final DoubleProperty x = new SimpleDoubleProperty(0);
    
    public double getX()
    {
        return x.get();
    }
    
    public void setX(double x)
    {
        this.x.set(x);
    }
    
    public DoubleProperty xProperty()
    {
        return x;
    }
    
    private final DoubleProperty y = new SimpleDoubleProperty(0);
    
    public double getY()
    {
        return y.get();
    }
    
    public void setY(double y)
    {
        this.y.set(y);
    }
    
    public DoubleProperty yProperty()
    {
        return y;
    }
    
    /* --- Update --- */
    
    /*
     * Updates the position of the object based on the amount of time (in seconds) passed since the
     * previous update.
     */
    public abstract void update(double deltaTime);
}
