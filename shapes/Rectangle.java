package shapes;

import java.awt.*;

public class Rectangle extends FillableShape {

	public Rectangle(Point p1, Point p2, boolean filled, Color color) {
		super(p1, p2, color, filled);
	}

	@Override
	public ShapeType getType() {
		return ShapeType.Rectangle;
	}

	public String toString() {
		return "rect;" + super.toString();
	}

	@Override
	public Rectangle clone() {
		return (Rectangle) super.clone();
	}
}
