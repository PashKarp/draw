package logic.States;

import logic.DrawingController;
import shapes.Rectangle;
import shapes.Shape;

import java.awt.*;

public class NewRectangleState extends DraggableShapeCreationState {
    public NewRectangleState(DrawingController c) {
        super(c);
    }

    @Override
    protected Shape createShape(Point p) {
        return new Rectangle(p, p, controller.getFill(), controller.getColor());
    }
}
