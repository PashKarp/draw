package shapes;

import java.awt.Color;
import java.awt.Point;

public abstract class Shape implements Cloneable {

	protected Point point1;
	protected Point point2;

	protected Color color;
	protected double strokeWidth;

	VectorDrawing drawing;

	private static int lastId = 0;
	private int id;

	public Shape(Point p) {
		point1 = p;
		point2 = new Point(p.x + 25, p.y + 25);
		color = Color.BLACK;
		strokeWidth = 2;
		id = lastId;
		lastId++;
	}

	public Color getColor() {
		return color;
	}

	public Point getPosition() {
		return new Point(Math.min(point1.x, point2.x), Math.min(point1.y,
				point2.y));
	}

	public Point getSize() {
		return new Point(Math.abs(point2.x - point1.x), Math.abs(point2.y
				- point1.y));
	}

	public Point getPoint1() {
		return (Point) point1.clone();
	}

	public Point getPoint2() {
		return (Point) point2.clone();
	}

	public abstract ShapeType getType();

	public boolean isSelected() {
		if (drawing != null) {
			return drawing.getSelection().contains(this);
		}

		return false;
	}

	public double getStrokeWidth() {
		return strokeWidth;
	}

	/**
	 * Checks if the Shape contains the given point. Has a 2 pixel margin in all
	 * directions.
	 * 
	 * @param p
	 *            point to check
	 * @return true if shape includes given point
	 */
	public boolean includes(Point p) {
		if ((p.x - getPosition().x > -2)
				&& (p.x - getPosition().x < getSize().x + 2)
				&& (p.y - getPosition().y > -2)
				&& (p.y - getPosition().y < getSize().y + 2)) {
			return true;
		}
		return false;
	}

	public Shape move(int x, int y) {
		Shape shape = clone();
		shape.point1 = new Point(point1.x + x, point1.y + y);
		shape.point2 = new Point(point2.x + x, point2.y + y);

		return shape;
	}

	public Shape setColor(Color c) {
		Shape shape = clone();
		shape.color = c;

		return shape;
	}

	public Shape setPoint1(Point p) {
		Shape shape = clone();
		shape.point1 = p;

		return shape;
	}

	public Shape setPoint2(Point p) {
		Shape shape = clone();
		shape.point2 = p;

		return shape;
	}

	public Shape updatePoint2(Point m) {
		return this.setPoint2(new Point(this.point2.x + m.x, this.point2.y + m.y));
	}

	public Shape setDrawing(VectorDrawing d) {
		Shape shape = clone();
		shape.drawing = d;

		return shape;
	}

	public String toString() {
		String str = "" + point1.x;
		str += "," + point1.y;
		str += ";" + point2.x;
		str += "," + point2.y;
		str += ";" + color.getRGB();
		return str;

	}

    @Override
    public Shape clone() {
        try {
            Shape clone = (Shape) super.clone();

			clone.id = id;
			clone.point1 = (Point) point1.clone();
			clone.point2 = (Point) point2.clone();
			clone.strokeWidth = strokeWidth;
			clone.color = color;
			clone.drawing = drawing;

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Shape) {
			return ((Shape) obj).id == id && ((Shape) obj).getType() == getType();
		}

		return false;
	}
}
