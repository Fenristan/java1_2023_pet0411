package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Laser extends GameEntity implements DrawableSimulable, Collisionable {

	private Point2D position;
	private Point2D speed;
	private Point2D size;
	private Game game;
	private boolean fired = false;
	
	public Laser(Game game, Point2D start, Point2D speed, Point2D size)
	{
		super(game,start);
		this.game=game;
		this.position=start;
		this.speed=speed;
		this.size=size;
	}

	public void collision()
	{
		reset();
	}

	public Rectangle2D getBoundingBox()
	{
		return new Rectangle2D(position.getX(), position.getY(), size.getX(), size.getY());
	}
	
	public void simulate(double timeStamp)
	{
		if (!fired) {
			return;
		}
		position = position.add(speed.multiply(timeStamp));
		if(position.getY()>game.getHeight()-size.getY())
		{
			//speed = speed.multiply(-1);
			reset();
		}
		else if(position.getY()+size.getY()<0)
		{
			reset();
		}

		/*if(position.getY()>game.getHeight()-10)
		{
			speed = new Point2D(speed.getX(),speed.getY()*-1);
		}*/
	}
	@Override
	public boolean intersects(Rectangle2D r) {
		return getBoundingBox().intersects(r);
	}

	public void hitBy(Collisionable another) {
		collision();
	}

	@Override
	protected void drawInternal(GraphicsContext gc) {
		gc.setFill(Color.WHITESMOKE);
		double x = position.getX();
		double y = game.getHeight() - position.getY();
		//gc.fillOval(x, y, size, size);
		gc.fillRect(x, y, size.getX(), size.getY());
	}

	public void fire(Point2D position)
	{
		if (fired) {
			return;
		}
		fired = true;
		this.position = new Point2D(position.getX(),game.getHeight()-position.getY());

	}
	private void reset()
	{
		fired = false;
		this.position = new Point2D(0,0);
	}


}
