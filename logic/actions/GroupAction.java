package logic.actions;

import shapes.ImmutableSelection;
import shapes.Shape;

public abstract class GroupAction implements DrawAction {
    protected ImmutableSelection selected;

    public GroupAction(ImmutableSelection selected) {
        this.selected = selected;
    }

    protected abstract void executeForShape(Shape s);
    protected abstract void undoForShape(Shape s);

    @Override
    public void execute() {
        for (Shape s : selected) {
            executeForShape(s);
        }
    }

    @Override
    public void redo() {
        execute();
    }

    @Override
    public void undo() {
        for (Shape s : selected) {
            undoForShape(s);
        }
    }
}
