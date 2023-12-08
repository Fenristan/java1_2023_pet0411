package lab;

import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;

import java.util.Random;

public class Game {
	private final double width;
	private final double height;
	private Laser laser1;
	private Laser alienLaser;

	private Cannon[] cannons = new Cannon[2];

	private DrawableSimulable[] objects;

	private Rectangle2D wall1;
	private Rectangle2D wall2;

	private Score score;

	private String playerName;
	private GameListener gameListener = new EmptyGameListener();
	boolean aliensChangeDirection = false;
	private Alien[] aliens = new Alien[55];
	private Bunker[] bunkers = new Bunker[4];

	int aliensInRow = 11;
	int aliensInColumn = 5;
	public Game(double width, double height)
	{
		this.width=width;
		this.height=height;

		this.laser1 = new Laser(this, new Point2D(0,0), new Point2D(0,100), new Point2D(5,15));
		alienLaser = new Laser(this,new Point2D(-50,-50),new Point2D(0,-100),new Point2D(5,15));

		cannons[0]=new Cannon(this, new Point2D(width/2,height-50), new Point2D(30,30));

		cannons[0].setHitListener(new HitListener(){
			@Override
			public void hit() {
				int lives = Game.this.cannons[0].getLives();
				gameListener.stateChanged(lives);
				if(lives==0)
				{
					gameListener.gameOver();
				}
			}

			@Override
			public void hit(Cannon cannon) {

			}

			@Override
			public void hitWall() {
			}
		});

		score = new Score(this,new Point2D(((width / 2) - 50),60));

		int bunkerXoffset = 0;
		for(int i = 0; i < bunkers.length; i++)
		{
			bunkers[i] = new Bunker(this,new Point2D(100+bunkerXoffset,100),new Point2D(50,50));
			bunkerXoffset+=150;
		}



		int rowcount = 0;
		Point2D startRowPosition = new Point2D(20,280);
		Point2D lastPosition = startRowPosition;
		int colOffset = 40;
		for(int i = 0; i < aliensInRow*aliensInColumn; i++)
		{
			if(rowcount==aliensInRow)
			{
				lastPosition = new Point2D(startRowPosition.getX(),lastPosition.getY()+40);
				rowcount = 0;
			}
			aliens[i] = new Alien(0,this,lastPosition,new Point2D(1,0),new Point2D(40,40));
			lastPosition = new Point2D(lastPosition.getX()+colOffset,lastPosition.getY());
			rowcount++;
		}
		for(Alien alien:aliens)
		{
			alien.setHitListener(new HitListener(){
				@Override
				public void hit() {
					Game.this.score.increaseScore(0);
					gameListener.stateChanged(Game.this.score);

				}

				@Override
				public void hit(Cannon cannon) {
					gameListener.gameOver();
				}

				@Override
				public void hitWall() {
					aliensChangeDirection = true;
				}
			});
		}

		objects = new DrawableSimulable[63];
		objects[0] = laser1;
		objects[1] = cannons[0];
		//objects[3] = net;
		objects[2] = score;

		objects[3] = bunkers[0];
		objects[4] = bunkers[1];
		objects[5] = bunkers[2];
		objects[6] = bunkers[3];

		for(int i = 7; i < 62; i++)
		{
			objects[i]=aliens[i-7];
		}

		objects[62] = alienLaser;

	}
	
	public void draw(GraphicsContext gc)
	{
		gc.setFill(Color.BLACK);
		//gc.clearRect(0, 0, this.getWidth(), this.getHeight());
		gc.fillRect(0, 0, this.getWidth(), this.getHeight());


		for(int i=0; i<objects.length;i++)
		{
			objects[i].draw(gc);
		}

		gc.setFill(Color.WHITESMOKE);
		gc.fillRect(0,0,this.width,10);
		gc.fillRect(0,this.height-10,this.width,10);


	}

	public void simulate(double t)
	{

		for(int i=0; i<objects.length;i++)
		{
			objects[i].simulate(t);
		}
		Random rand = new Random();
		for(int i = 0; i < aliensInRow; i++)
		{
			if(aliens[i].isAlive())
			{
				if(rand.nextInt()%11 == 0)
				{
					Point2D alienPosition = aliens[i].getPosition();
					alienLaser = (Laser) objects[62];
					alienLaser.fire(new Point2D(alienPosition.getX(),height - alienPosition.getY()+50));
				}
			}
		}
		for(int i=aliensInRow; i<aliens.length;i++)
		{
			if(aliens[i-aliensInRow].isAlive())
			{

			}
			else if(aliens[i].isAlive())
			{
				if(rand.nextInt()%11 == 0)
				{
					Point2D alienPosition = aliens[i].getPosition();
					alienLaser = (Laser) objects[62];
					alienLaser.fire(new Point2D(alienPosition.getX(),height - alienPosition.getY()+50));
				}

			}
		}
		for(int i=0; i<objects.length;i++)
		{
			if(objects[i] instanceof Collisionable collisionableObject)
			{
				if(objects[i] instanceof Alien)
				{
					if(aliensChangeDirection == true)
					{
						Alien alien = (Alien)objects[i];
						alien.changeDirection();
						objects[i]=alien;
					}
				}
				for(int j=0; j<objects.length;j++)
				{
					if(objects[j] instanceof Collisionable anotherObject)
					{
						if(collisionableObject!=anotherObject)
						{
							if(collisionableObject.intersects(anotherObject.getBoundingBox()))
							{
								collisionableObject.hitBy(anotherObject);
								anotherObject.hitBy(collisionableObject);
								if((score.getScore(0)+score.getScore(1))==55)
								{
									gameListener.gameOver();
								}
							}
						}
					}
				}
			}
		}
		aliensChangeDirection = false;
	}
	public Point2D getCanvasPoint(Point2D worldPoint) {
		return new Point2D(worldPoint.getX(), height - worldPoint.getY());
	}
	public double getWidth()
	{
		return width;
	}
	
	public double getHeight()
	{
		return height;
	}

	public void moveLeftBatLeft() {
		Cannon b = (Cannon)objects[1];
		b.moveLeft();
	}
	public void moveLeftBatRight() {
		Cannon b = (Cannon)objects[1];
		b.moveRight();
	}

	public void stopMoveLeftBat() {
		Cannon b = (Cannon)objects[1];
		b.moveStop();
	}

	public void fire() {
		Cannon b = (Cannon)objects[1];
		Laser laser = (Laser)objects[0];
		laser.fire(new Point2D(b.getPosition().getX()+b.getSize().getX()/2,b.getPosition().getY()-b.getSize().getY()));
	}

	public void setPlayerName(String text) {
		playerName = text;
	}


	public void setGameListener(GameListener listener) {
		this.gameListener = listener;
	}
}
