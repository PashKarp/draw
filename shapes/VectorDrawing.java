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

public class VectorDrawing implements Iterable<Shape> {

	private static final long serialVersionUID = 0;

	private Selection selection;

	private ArrayList<Shape> shapes;

	private ArrayList<DrawingListener> listeners;

	public VectorDrawing() {
		shapes = new ArrayList<Shape>();
		listeners = new ArrayList<DrawingListener>();
		selection = new Selection();
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
		Shape shape = s.setDrawing(this);
		shapes.add(shape);
		fireShapeAppended(shape);
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

		Shape updatedShape = shape.setColor(color);
		shapes.add(updatedShape);

		fireShapeUpdated(updatedShape);
	}

	public void fillShape(Shape s) {
		if (s instanceof FillableShape) {
			Shape shape = getRealShape(s);
			shapes.remove(shape);

			FillableShape fs = (FillableShape) shape;

			Shape updatedShape = fs.setFilled(!(fs).getFilled());
			shapes.add(updatedShape);

			fireShapeUpdated(updatedShape);
		}
	}

	public void moveShape(Shape s, Point movement) {
		Shape shape = getRealShape(s);
		shapes.remove(shape);

		Shape updatedShape = shape.move(movement.x, movement.y);
		shapes.add(updatedShape);

		fireShapeUpdated(updatedShape);
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

	public void raise(Shape s) {
		int index = shapes.indexOf(s);
		if (index > 0) {
			shapes.remove(s);
			shapes.add(--index, s);
		}
	}

	public void removeShape(Shape s) {
		shapes.remove(s);

		fireShapeDeleted(s);
	}

	public ImmutableSelection getSelection() {
		return selection.clone();
	}

	public void addShapeToSelection(Shape s) {
		selection.add(s);

		fireShapeAppendedToSelection(s);
	}

	public void emptySelection() {
		selection.empty();

		fireSelectionCleared();
	}

	public void addDrawingListener(DrawingListener listener) {
		if (! listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeDrawingListener(DrawingListener listener) {
		listeners.remove(listener);
	}

	private void fireShapeAppended(Shape s) {
		for (DrawingListener listener : listeners) {
			listener.shapeAppended(s);
		}
	}

	private void fireShapeDeleted(Shape s) {
		for (DrawingListener listener : listeners) {
			listener.shapeDeleted(s);
		}
	}

	private void fireShapeUpdated(Shape s) {
		for (DrawingListener listener : listeners) {
			listener.shapeUpdated(s);
		}
	}

	private void fireShapeAppendedToSelection(Shape s) {
		for (DrawingListener listener : listeners) {
			listener.shapeAppendedToSelection(s);
		}
	}

	private void fireSelectionCleared() {
		for (DrawingListener listener : listeners) {
			listener.selectionCleared();
		}
	}
}