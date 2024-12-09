package gui.Adapters;

import shapes.Shape;

import java.awt.*;

public abstract class ShapeAdapter {
    protected Shape shape;

    public ShapeAdapter(Shape shape) {
        this.shape = shape;
    }

    public void draw(Graphics g) {
        g.setColor(shape.getColor());
        drawShape(g);
        if (shape.isSelected()) {
            drawSelectionIndicator(g);
        }
    }

    protected Point getSelectionIndicatorPoint1(Graphics g) {
        return shape.getPosition();
    }

    protected Point getSelectionIndicatorPoint2(Graphics g) {
        return new Point(shape.getPosition().x + shape.getSize().x, shape.getPosition().y
                + shape.getSize().y);
    }

    public void drawSelectionIndicator(Graphics g) {

        ((Graphics2D) g).setStroke(new BasicStroke((float) 1.0));
        g.setColor(new Color(255, 0, 255));

        int len = 10;
        int off = 5;

        Point p1 = getSelectionIndicatorPoint1(g);
        Point p2 = getSelectionIndicatorPoint2(g);

        g.drawPolyline(
                // left up
                new int[] { p1.x - off, p1.x - off, p1.x - off + len },
                new int[] { p1.y - off + len, p1.y - off, p1.y - off }, 3);

        g.drawPolyline(
                // right down
                new int[] { p2.x + off - len, p2.x + off, p2.x + off },
                new int[] { p2.y + off, p2.y + off, p2.y + off - len }, 3);

        g.drawPolyline(
                // right up
                new int[] { p2.x + off - len, p2.x + off, p2.x + off },
                new int[] { p1.y - off, p1.y - off, p1.y - off + len }, 3);

        g.drawPolyline(
                // left down
                new int[] { p1.x - off, p1.x - off, p1.x - off + len },
                new int[] { p2.y + off - len, p2.y + off, p2.y + off }, 3);

    }

    public abstract void drawShape(Graphics g);

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ShapeAdapter) {
            return ((ShapeAdapter) obj).shape.equals(shape);
        }
        
        return false;
    }
}
