package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Alien extends GameEntity implements Collisionable {
    private int id;
    private Point2D position;
    private Point2D direction;
    private Game game;
    private Point2D size;
    private HitListener hitListener = new EmptyHitListener();
    private Image image;

    private boolean alive = true;

    public Alien(int id, Game game, Point2D position, Point2D direction, Point2D size)
    {
        super(game,position);
        this.id = id;
        this.game=game;
        this.position=position;
        this.direction=direction;
        this.size=size;
        image = new Image(getClass().getResourceAsStream("200w.gif"), size.getX(), size.getY(),
                true, true);
    }
    @Override
    protected void drawInternal(GraphicsContext gc) {
        if(alive)
        {
            gc.save();
            Point2D canvasPosition = game.getCanvasPoint(position);
            gc.drawImage(image, canvasPosition.getX(), canvasPosition.getY());
            gc.restore();
        }

    }
    @Override
    public void simulate(double timeDelta) {
        if(alive)
        {
            if(position.getX() + direction.getX() < 0)
            {
                hitListener.hitWall();
            }
            else if (position.getX() + direction.getX() > game.getWidth()-size.getX())
            {
                hitListener.hitWall();
            }
            else
            {
                position = new Point2D(position.getX()+direction.getX(), position.getY()+direction.getY());
            }
        }
    }
    @Override
    public Rectangle2D getBoundingBox() {
        return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        return getBoundingBox().intersects(r);
    }

    @Override
    public void hitBy(Collisionable another) {
        if(another instanceof Laser)
        {
            alive = false;
            hitListener.hit();

            position = new Point2D(-50,-50);
        }
        else if(another instanceof Cannon)
        {
            hitListener.hit((Cannon)another);
        }

    }
    public void setHitListener(HitListener hitListener) {
        this.hitListener = hitListener;
    }

    public void changeDirection() {
        position = new Point2D(position.getX(), position.getY()-10);
        direction = new Point2D(-direction.getX(), direction.getY());
    }

    public Point2D getPosition() {
        return position;
    }

    public boolean isAlive() {
        return alive;
    }
}
