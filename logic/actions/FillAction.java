package logic.actions;

import shapes.ImmutableSelection;
import shapes.Shape;
import shapes.VectorDrawing;

/**
 * FillAction implements a undoable action where the fill status of all the
 * Shapes in a given Selection are toggled.
 */
public class FillAction extends GroupAction {

	VectorDrawing d;

	/**
	 * Creates a FillAction that filps the fill status of all FillableShape
	 * instances in the given selection.
	 * 
	 * @param s
	 *            a selection which contains the shapes to be modified
	 */
	public FillAction(ImmutableSelection s, VectorDrawing d) {
		super(s);

		this.d = d;
	}

	@Override
	protected void executeForShape(Shape s) {
		d.fillShape(s);
	}

	@Override
	protected void undoForShape(Shape s) {
		d.fillShape(s);
	}

	public String getDescription() {
		return null;
	}
}
