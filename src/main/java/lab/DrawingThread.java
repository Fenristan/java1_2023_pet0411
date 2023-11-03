package lab;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class DrawingThread extends AnimationTimer {

	private final GraphicsContext gc;
	private long lastTime;
	
	private final Game game;
	private final Canvas canvas;
	
	public DrawingThread(Canvas canvas, Game game) {
		this.canvas = canvas;
		this.gc = canvas.getGraphicsContext2D();
		this.game=game;
	}

	/**
	  * Draws objects into the canvas. Put you code here. 
	 */
	@Override
	public void handle(long now) {
		if (lastTime > 0) {
			double deltaT = (now - lastTime) / 1e9;
			game.simulate(deltaT); // call simulate on world
		}
		game.draw(gc); //call draw on world
		lastTime= now;

	}

}
