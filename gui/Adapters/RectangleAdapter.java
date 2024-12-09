package gui.Adapters;

import shapes.FillableShape;
import shapes.Rectangle;

import java.awt.*;

public class RectangleAdapter extends FillableShapeAdapter {

    public RectangleAdapter(Rectangle shape) {
        super(shape);
    }

    @Override
    public void drawFilled(Graphics g) {
        g.fillRect(shape.getPosition().x, shape.getPosition().y, shape.getSize().x, shape.getSize().y);
    }

    @Override
    public void drawNonFilled(Graphics g) {
        g.drawRect(shape.getPosition().x, shape.getPosition().y, shape.getSize().x, shape.getSize().y);
    }
}
