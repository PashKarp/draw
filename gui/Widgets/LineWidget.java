package gui.Widgets;

import shapes.Line;

import java.awt.*;

public class LineWidget extends GUIShapeWidget {
    public LineWidget(Line shape) {
        super(shape);
    }

    @Override
    public void drawShape(Graphics g) {
        ((Graphics2D) g).setStroke(new BasicStroke((float) shape.getStrokeWidth()));

        g.drawLine(shape.getPoint1().x, shape.getPoint1().y, shape.getPoint2().x, shape.getPoint2().y);
    }
}
