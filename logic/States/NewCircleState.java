package logic.States;

import logic.DrawingController;
import shapes.Circle;
import shapes.Shape;

import java.awt.*;

public class NewCircleState extends DraggableShapeCreationState {
    public NewCircleState(DrawingController c) {
        super(c);
    }

    @Override
    protected Shape createShape(Point p) {
        return new Circle(p, p, controller.getFill(), controller.getColor());
    }
}
