package lab;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Net extends GameEntity implements DrawableSimulable {
    private Point2D position;
    private Game game;

    public Net(Game game, Point2D position)
    {
        super(game,position);
        this.game=game;
        this.position=position;
    }

    @Override
    protected void drawInternal(GraphicsContext gc) {
        for(int i=0;i<10;i++)
        {
            gc.fillRect(position.getX(), position.getY()*i, 19.5, 19.5);
        }
    }


    @Override
    public void simulate(double timeDelta) {

    }
}
