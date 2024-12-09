package logic.actions;

import java.awt.Point;

import shapes.ImmutableSelection;
import shapes.Shape;
import shapes.VectorDrawing;

/**
 * MoveAction implements a single undoable action where all the Shapes in a
 * given Selection are moved.
 */
public class MoveAction extends MoveUpdatableAction {

	ImmutableSelection selected;
	Point movement;

	VectorDrawing d;

	/**
	 * Creates a MoveAction that moves all Shapes in the given Selection in the
	 * direction given by the point. The movement is relative to the shapes
	 * original position.
	 * 
	 * @param s
	 *            a selection which contains the shapes to be moved
	 * @param m
	 *            the amount the shapes should be moved, relative to the
	 *            original position
	 */
	public MoveAction(ImmutableSelection s, Point m, VectorDrawing d) {
		this.selected = s;
		this.movement = m;
		this.d = d;
	}

	public void execute() {
		for (Shape s : selected) {
			d.moveShape(s, movement);
		}
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		Point reverseMovement = new Point(-movement.x, -movement.y);

		for (Shape s : selected) {
			d.moveShape(s, reverseMovement);
		}
	}

	public MoveAction moveUpdate(Point m) {
		Point newPoint = new Point(this.movement.x + m.x, this.movement.y + m.y);
		return new MoveAction(this.selected, newPoint, d);
	}
}
