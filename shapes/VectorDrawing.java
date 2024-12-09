package shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class VectorDrawing extends JPanel implements Iterable<Shape> {

	private static final long serialVersionUID = 0;

	private Selection selection;

	private ArrayList<Shape> shapes;

	public VectorDrawing(Dimension size) {
		shapes = new ArrayList<Shape>(0);

		selection = new Selection();

		this.setPreferredSize(size);
		setBorder(BorderFactory.createLineBorder(Color.black));
		setBackground(Color.WHITE);
	}

	public BufferedImage getImage() {

		BufferedImage bi = new BufferedImage(getPreferredSize().width,
				getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		this.print(g);
		return bi;
	}

	public Shape getShapeAt(Point p) {
		int index = shapes.size() - 1;
		while (index >= 0) {

			if (shapes.get(index).includes(p)) {
				return shapes.get(index);
			}
			index--;
		}
		return null;

	}

	public void insertShape(Shape s) {
		shapes.add(s.setDrawing(this));
	}

	private Shape getRealShape(Shape s) {
		int shapeIndex = shapes.indexOf(s);
		if (shapeIndex != -1) {
			return shapes.get(shapeIndex);
		}

		return null;
	}

	public void colorShape(Shape s, Color color) {
		Shape shape = getRealShape(s);

		shapes.remove(shape);
		shapes.add(shape.setColor(color));
	}

	public void fillShape(Shape s) {
		if (s instanceof FillableShape) {
			Shape shape = getRealShape(s);
			shapes.remove(shape);

			FillableShape fs = (FillableShape) shape;
			shapes.add(fs.setFilled(!(fs).getFilled()));
		}
	}

	public void moveShape(Shape s, Point movement) {
		Shape shape = getRealShape(s);
		shapes.remove(shape);
		shapes.add(shape.move(movement.x, movement.y));
	}

	@Override
	public Iterator<Shape> iterator() {
		return shapes.iterator();
	}

	public void listShapes() {
		System.out.println("---");
		for (Shape s : shapes) {
			System.out.println(s);
		}
		System.out.println("---");
	}

	public void lower(Shape s) {
		int index = shapes.indexOf(s);
		if (index < shapes.size() - 1) {
			shapes.remove(s);
			shapes.add(index, s);
		}
	}

	public int nShapes() {
		return shapes.size();
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		for (Shape s : shapes) {
			s.draw(g);
		}
	}

	public void raise(Shape s) {
		int index = shapes.indexOf(s);
		if (index > 0) {
			shapes.remove(s);
			shapes.add(--index, s);
		}
	}

	public void removeShape(Shape s) {
		shapes.remove(s);
	}

	public ImmutableSelection getSelection() {
		return selection.clone();
	}

	public void addShapeToSelection(Shape s) {
		selection.add(s);
	}

	public void emptySelection() {
		selection.empty();
	}
}
