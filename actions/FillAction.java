package actions;

import shapes.ImmutableSelection;
import shapes.FillableShape;
import shapes.Shape;
import shapes.VectorDrawing;

/**
 * FillAction implements a undoable action where the fill status of all the
 * Shapes in a given Selection are toggled.
 */
public class FillAction implements DrawAction {

	ImmutableSelection selected;
	VectorDrawing d;

	/**
	 * Creates a FillAction that filps the fill status of all FillableShape
	 * instances in the given selection.
	 * 
	 * @param s
	 *            a selection which contains the shapes to be modified
	 */
	public FillAction(ImmutableSelection s, VectorDrawing d) {
		this.selected = s;
		this.d = d;
	}

	public void execute() {
		for (Shape s : selected) {
			d.fillShape(s);
		}
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		execute();
	}

}
