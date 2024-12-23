package gui.Widgets;

import shapes.Circle;

import java.awt.*;

public class CircleWidget extends FillableShapeWidget {
    public CircleWidget(Circle shape) {
        super(shape);
    }

    @Override
    public void drawFilled(Graphics g) {
        g.fillOval(shape.getPosition().x, shape.getPosition().y, shape.getSize().x, shape.getSize().y);
    }

    @Override
    public void drawNonFilled(Graphics g) {
        g.drawOval(shape.getPosition().x, shape.getPosition().y, shape.getSize().x, shape.getSize().y);
    }
}
