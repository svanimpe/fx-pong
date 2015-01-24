package svanimpe.pong.ai;

import svanimpe.pong.Game;
import svanimpe.pong.objects.Paddle;

public abstract class PaddleAi
{
    /* --- Construction and final properties --- */

    private final Paddle paddle;
    private final Game game;
    
    protected PaddleAi(Paddle paddle, Game game)
    {
        this.paddle = paddle;
        this.game = game;
    }

    public Game getGame()
    {
        return game;
    }
    
    public Paddle getPaddle()
    {
        return paddle;
    }

    /* --- Update --- */
    
    /*
     * Updates the state of the paddle based on the amount of time (in seconds) passed since the
     * previous update.
     */
    public abstract void update(double deltaTime);
}
