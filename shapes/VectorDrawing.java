package shapes;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class VectorDrawing implements Iterable<Shape> {

	private static final long serialVersionUID = 0;

	private ArrayList<Shape> shapes;

	private ArrayList<DrawingListener> listeners;

	private HashMap<ShapeType, Shape> prototypes;

	public VectorDrawing() {
		shapes = new ArrayList<Shape>();
		listeners = new ArrayList<DrawingListener>();

		prototypes = new HashMap<ShapeType, Shape>();

		prototypes.put(ShapeType.Text, new Text(0, 0, 12, "test"));
		prototypes.put(ShapeType.Rectangle, new Rectangle(0, 0, false));
		prototypes.put(ShapeType.Circle, new Circle(0, 0, false));
		prototypes.put(ShapeType.Line, new Line(0, 0));
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
		shapes.add(s);
		fireShapeAppended(s);
	}

	private Shape getRealShape(Shape s) {
		int shapeIndex = shapes.indexOf(s);
		if (shapeIndex != -1) {
			return shapes.get(shapeIndex);
		}

		return null;
	}

	public Shape getShapePrototype(ShapeType type) {
		return prototypes.get(type).copy();
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
		Selection selection = new Selection();

		for (Shape shape : shapes) {
			if (shape.isSelected()) {
				selection.add(shape);
			}
		}

		return selection;
	}

	public void addShapeToSelection(Shape s) {
		Shape realShape = getRealShape(s);

		shapes.remove(realShape);

		Shape selectedShape = realShape.setSelected(true);

		shapes.add(selectedShape);

		fireShapeAppendedToSelection(selectedShape);
	}

	public void addAllShapesToSelection() {
		shapes = (ArrayList<Shape>) shapes.stream().map(s -> s.setSelected(true)).collect(Collectors.toList());

		for (Shape shape : shapes) {
			fireShapeAppendedToSelection(shape);
		}
	}

	public void emptySelection() {
		shapes = (ArrayList<Shape>) shapes.stream().map(s -> s.setSelected(false)).collect(Collectors.toList());

		fireSelectionCleared((ArrayList<Shape>) shapes.clone());
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

	private void fireSelectionCleared(ArrayList<Shape> shapes) {
		for (DrawingListener listener : listeners) {
			listener.selectionCleared(shapes);
		}
	}
}
