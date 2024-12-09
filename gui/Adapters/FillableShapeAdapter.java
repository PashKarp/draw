package gui.Adapters;

import shapes.FillableShape;
import shapes.Shape;

import java.awt.*;

public abstract class FillableShapeAdapter extends ShapeAdapter {
    public FillableShapeAdapter(FillableShape shape) {
        super(shape);
    }

    public abstract void drawFilled(Graphics g);

    public abstract void drawNonFilled(Graphics g);

    public void drawShape(Graphics g) {

        ((Graphics2D) g).setStroke(new BasicStroke((float) shape.getStrokeWidth()));

        if (((FillableShape) shape).getFilled()) {
            drawFilled(g);
        }
        else {
            drawNonFilled(g);
        }
    }
}
