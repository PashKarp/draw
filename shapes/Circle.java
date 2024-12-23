package shapes;

import java.awt.*;

public class Circle extends FillableShape {

	public Circle(Point p1, Point p2, boolean filled, Color color) {
		super(p1, p2, color, filled);
	}

	@Override
	public ShapeType getType() {
		return ShapeType.Circle;
	}

	public String toString() {
		return "circ;" + super.toString();
	}

	@Override
	public Circle clone() {
		return (Circle) super.clone();
	}
}
