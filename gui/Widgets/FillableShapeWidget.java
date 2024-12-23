package gui.Widgets;

import shapes.FillableShape;

import java.awt.*;

public abstract class FillableShapeWidget extends GUIShapeWidget {
    public FillableShapeWidget(FillableShape shape) {
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
