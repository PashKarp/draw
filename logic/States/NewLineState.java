package logic.States;

import logic.DrawingController;
import shapes.Line;
import shapes.Shape;

import java.awt.Point;

public class NewLineState extends DraggableShapeCreationState {
    public NewLineState(DrawingController c) {
        super(c);
    }

    @Override
    protected Shape createShape(Point p) {
        return new Line(p, p, controller.getColor());
    }
}
