package lab;

import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class Cannon extends GameEntity implements DrawableSimulable, Collisionable {

	private Point2D direction = new Point2D(0,0);
	private Point2D position;
	private Point2D size;
	private Game game;
	private Image image;
	private int lives = 3;
	private HitListener hitListener = new EmptyHitListener();
	
	public Cannon(Game game, Point2D position, Point2D size)
	{
		super(game,position);
		this.game = game;
		this.position=position;
		this.size=size;
		image = new Image(getClass().getResourceAsStream("cannon.jpg"), size.getX(), size.getY(),
				true, true);
	}

	public Rectangle2D getBoundingBox()
	{
		return new Rectangle2D(position.getX(), game.getHeight()-position.getY(), size.getX(), size.getY());
	}
	public Point2D getPosition()
	{
		return position;
	}
	public Point2D getSize()
	{
		return size;
	}
	private double speed = 100;
	public void simulate(double timeStamp)
	{
		//direction += speed * timeStamp;

		if(position.getX() + direction.getX() < 0)
		{

		}
		else if (position.getX() + direction.getX() > game.getWidth()-size.getX())
		{

		}
		else
		{
			position = new Point2D(position.getX()+direction.getX(), position.getY()+direction.getY());
		}

	}
	@Override
	public boolean intersects(Rectangle2D r) {
		return getBoundingBox().intersects(r);
	}

	public void hitBy(Collisionable another) {
		if(another instanceof Laser)
		{
			lives--;
			hitListener.hit();
		}
	}

	@Override
	protected void drawInternal(GraphicsContext gc) {
		gc.save();
		gc.drawImage(image, position.getX()+direction.getX(), position.getY()+direction.getY());
		gc.restore();
	}

	public void moveUp() {
		direction = new Point2D(0,-2);
	}

	public void moveDown() {
		direction = new Point2D(0,2);
	}

	public void moveLeft() {
		direction = new Point2D(-2,0);
	}

	public void moveRight() {
		direction = new Point2D(2,0);
	}

	public void moveStop() {
		direction = new Point2D(0,0);
	}

	public void setHitListener(HitListener hitListener) {
		this.hitListener = hitListener;
	}
	public int getLives()
	{
		return lives;
	}
}
