package logic.States;

import logic.DrawingController;
import logic.actions.AddAction;
import logic.actions.DrawAction;
import shapes.Shape;

import java.awt.*;

public abstract class DraggableShapeCreationState extends DrawingState {
    public DraggableShapeCreationState(DrawingController c) {
        super(c);
    }

    protected Shape newShape;

    protected abstract Shape createShape(Point p);

    @Override
    public void processPress(Point p, boolean isAdditionalButtonPressed) {
        if (newShape != null) {
            controller.fireConstructionEnd(newShape);
        }

        newShape = createShape(p);

        controller.fireConstructionStart(newShape);
    }

    @Override
    public void processDrag(int difX, int difY) {
        if (newShape != null) {
            Point newPoint2 = new Point(newShape.getPoint2().x + difX, newShape.getPoint2().y + difY);
            newShape = newShape.setPoint2(newPoint2);

            controller.fireConstructionUpdate(newShape);
        }
    }

    @Override
    public void processRelease() {
        if (newShape != null) {
            controller.fireConstructionEnd(newShape);

            DrawAction add = new AddAction(controller.getDrawing(), newShape);
            controller.addAction(add);
            newShape = null;
        }
    }

    @Override
    public void processStateChange() {
        if (newShape != null) {
            controller.fireConstructionEnd(newShape);
            newShape = null;
        }
    }
}
