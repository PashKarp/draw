package shapes;

import java.awt.*;

public abstract class FillableShape extends Shape {

	protected boolean filled;

	public FillableShape(int xpos, int ypos) {
		super(new Point(xpos, ypos));
		filled = false;
	}

	public abstract void drawFilled(Graphics g);

	public abstract void drawNonFilled(Graphics g);

	public void drawShape(Graphics g) {

		((Graphics2D) g).setStroke(new BasicStroke((float) strokeWidth));

		if (filled) {
			drawFilled(g);
		}
		else {
			drawNonFilled(g);
		}
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
