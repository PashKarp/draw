package logic.actions;

import java.awt.Point;

import shapes.ImmutableSelection;
import shapes.Shape;
import shapes.VectorDrawing;

/**
 * MoveAction implements a single undoable action where all the Shapes in a
 * given Selection are moved.
 */
public class MoveAction implements MergeableAction {

	ImmutableSelection selected;
	Point movement;

	VectorDrawing d;
	boolean isEnd;

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
	public MoveAction(ImmutableSelection s, Point m, VectorDrawing d, boolean isEnd) {
		this.selected = s;
		this.movement = m;
		this.d = d;
		this.isEnd = isEnd;
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

	@Override
	public boolean canMerge(MergeableAction action) {
		return action instanceof MoveAction &&
				! this.isEnd &&
				this.selected.equals(((MoveAction) action).selected) &&
				this.d.equals(((MoveAction) action).d);
	}

	@Override
	public MergeableAction merge(MergeableAction action) {
		if (action instanceof MoveAction &&
				! this.isEnd &&
				this.selected.equals(((MoveAction) action).selected) &&
				this.d.equals(((MoveAction) action).d)) {
			Point movement = ((MoveAction) action).movement;

			Point actualMovement = new Point(this.movement.x + movement.x, this.movement.y + movement.y);

			return new MoveAction(this.selected, actualMovement, this.d, ((MoveAction) action).isEnd);
		}

		return this;
	}
}
