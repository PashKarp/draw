package shapes;

import java.awt.*;

public class Line extends Shape {

	public Line(Point p1, Point p2, Color color) {
		super(p1, p2, color);
	}

	@Override
	public ShapeType getType() {
		return ShapeType.Line;
	}

	@Override
	public String toString() {
		return "line;" + super.toString();
	}

	@Override
	public Line clone() {
		return (Line) super.clone();
	}
}
