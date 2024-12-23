package logic.actions;

import shapes.ImmutableSelection;
import shapes.VectorDrawing;
import shapes.Shape;

/**
 * DeleteAction implements a single undoable action where all Shapes in a given
 * Selection are added to a Drawing.
 */
public class DeleteAction extends GroupAction {

	VectorDrawing d;

	/**
	 * Creates an DeleteAction that removes all shapes in the given Selection
	 * from the given Drawing.
	 * 
	 * @param drawing
	 *            the drawing into which the shape should be added.
	 * @param selection
	 *            the shape to be added.
	 */
	public DeleteAction(VectorDrawing drawing, ImmutableSelection selection) {
		super(selection);
		this.d = drawing;
	}

	@Override
	protected void executeForShape(Shape s) {
		d.removeShape(s);
	}

	@Override
	protected void undoForShape(Shape s) {
		d.insertShape(s);
	}

	public String getDescription() {
		return null;
	}

}
