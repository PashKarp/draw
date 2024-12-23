package shapes;

import java.awt.*;

public abstract class FillableShape extends Shape {

	protected boolean filled;

	public FillableShape(Point p1, Point p2, Color color, boolean isFilled) {
		super(p1, p2, color);
		filled = isFilled;
	}

	public boolean getFilled() {
		return filled;
	}

	public FillableShape setFilled(boolean f) {
		FillableShape shape = clone();

		shape.filled = f;

		return shape;
	}

	public String toString() {
		return super.toString() + ";" + (filled ? 1 : 0);
	}

	@Override
	public FillableShape clone() {
		FillableShape clone = (FillableShape) super.clone();

		clone.filled = filled;

		return clone;
	}
}
