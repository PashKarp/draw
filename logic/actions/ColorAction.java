package logic.actions;

import java.awt.Color;

import shapes.Shape;
import shapes.VectorDrawing;

/**
 * DeleteAction implements a single undoable action where the color of a Shape
 * are changed.
 */

public class ColorAction implements DrawAction {

	Shape shape;

	Color oldColor;
	Color newColor;

	VectorDrawing d;

	/**
	 * Creates an ColorAction that changes the color of a given Shape.
	 * 
	 * @param s
	 *            the shape to be modified.
	 * @param newColor
	 *            the new color for the shape.
	 */
	public ColorAction(Shape s, Color newColor, VectorDrawing d) {
		shape = s;
		this.oldColor = s.getColor();
		this.newColor = newColor;
		this.d = d;
	}

	public void execute() { d.colorShape(shape, newColor); }

	public String getDescription() {
		return null;
	}

	public void redo() {
		this.execute();
	}

	public void undo() {
		d.colorShape(shape, oldColor);
	}

}
