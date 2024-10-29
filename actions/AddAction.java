package actions;

import shapes.VectorDrawing;
import shapes.Shape;

import java.awt.*;

/**
 * AddAction implements a single undoable action where a Shape is added to a
 * Drawing.
 */
public class AddAction extends MoveUpdatableAction {

	VectorDrawing d;
	Shape s;

	/**
	 * Creates an AddAction that adds the given Shape to the given Drawing.
	 * 
	 * @param dr
	 *            the drawing into which the shape should be added.
	 * @param sh
	 *            the shape to be added.
	 */
	public AddAction(VectorDrawing dr, Shape sh) {
		this.d = dr;
		this.s = sh;
	}

	public void execute() {
		d.insertShape(s);
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		this.execute();
	}

	public void undo() {
		d.removeShape(s);
	}

	public AddAction moveUpdate(Point m) {
		this.s.updatePoint2(m);
		return this;
	}
}
