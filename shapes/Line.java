package shapes;

import java.awt.*;

public class Line extends Shape {

	public Line(int x, int y) {
		super(new Point(x, y));
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
