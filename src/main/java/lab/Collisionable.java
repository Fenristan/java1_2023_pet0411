package lab;

import javafx.geometry.Rectangle2D;

public interface Collisionable {
	Rectangle2D getBoundingBox();
	boolean intersects(Rectangle2D r);
	void hitBy(Collisionable another);
}
