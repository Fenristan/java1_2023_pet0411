package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Goal extends GameEntity implements Collisionable {
    private int id;
    private Point2D position;
    private Game game;
    private Point2D size;
    private Score score;
    private HitListener hitListener = new EmptyHitListener();

    public Goal(int id, Game game, Point2D position, Point2D size, Score score)
    {
        super(game,position);
        this.id = id;
        this.game=game;
        this.position=position;
        this.size=size;
        this.score = score;
    }

    @Override
    protected void drawInternal(GraphicsContext gc) {
        gc.setFill(Color.DARKRED);
        gc.fillRect(position.getX(),position.getY(),size.getX(),size.getY());
    }


    @Override
    public void simulate(double timeDelta) {

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
            hitListener.hit();
    }

    public void setHitListener(HitListener hitListener) {
        this.hitListener = hitListener;
    }
    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }
}
