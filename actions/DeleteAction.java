package actions;

import shapes.ImmutableSelection;
import shapes.VectorDrawing;
import shapes.Shape;

/**
 * DeleteAction implements a single undoable action where all Shapes in a given
 * Selection are added to a Drawing.
 */
public class DeleteAction implements DrawAction {

	VectorDrawing d;
	ImmutableSelection selection;

	int position;

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
		this.selection = selection;
		this.d = drawing;
	}

	public void execute() {
		for (Shape s : selection) {
			d.removeShape(s);
		}
	}

	public String getDescription() {
		return null;
	}

	public void redo() {
		execute();
	}

	public void undo() {
		for (Shape s : selection) {
			d.insertShape(s);
		}
	}

}
