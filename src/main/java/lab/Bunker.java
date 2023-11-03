package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Bunker extends GameEntity implements DrawableSimulable, Collisionable {
    private Point2D position;
    private Point2D size;
    private Game game;
    private Image image;
    private int lives = 4;
    private HitListener hitListener = new EmptyHitListener();

    public Bunker(Game game, Point2D position, Point2D size)
    {
        super(game,position);
        this.game = game;
        this.position=position;
        this.size=size;
        image = new Image(getClass().getResourceAsStream("bunker.jpeg"), size.getX(), size.getY(),
                true, true);
    }

    public Rectangle2D getBoundingBox()
    {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }
    public Point2D getPosition()
    {
        return position;
    }
    public Point2D getSize()
    {
        return size;
    }
    public void simulate(double timeStamp)
    {

    }
    @Override
    public boolean intersects(Rectangle2D r) {
        return getBoundingBox().intersects(r);
    }

    public void hitBy(Collisionable another) {
        if(another instanceof Laser)
        {
            lives--;
            //hitListener.hit();
        }
    }

    @Override
    protected void drawInternal(GraphicsContext gc) {
        gc.save();
        gc.drawImage(image, position.getX(),  game.getHeight()-position.getY());
        gc.restore();
    }

    /*public void setHitListener(HitListener hitListener) {
        this.hitListener = hitListener;
    }
    public int getLives()
    {
        return lives;
    }*/
}
