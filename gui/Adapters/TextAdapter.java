package gui.Adapters;

import shapes.Shape;
import shapes.Text;

import java.awt.*;

public class TextAdapter extends ShapeAdapter {
    public TextAdapter(Text shape) {
        super(shape);
    }

    @Override
    protected Point getSelectionIndicatorPoint1(Graphics g) {
        g.setFont(((Text) shape).getFont());
        int w = g.getFontMetrics().stringWidth(((Text) shape).getText());
        Shape s = shape.setPoint2(new Point(shape.getPoint1().x + w, shape.getPoint1().y - ((Text) shape).getFont().getSize()));

        return s.getPosition();
    }

    @Override
    protected Point getSelectionIndicatorPoint2(Graphics g) {
        g.setFont(((Text) shape).getFont());
        int w = g.getFontMetrics().stringWidth(((Text) shape).getText());
        Shape s = shape.setPoint2(new Point(shape.getPoint1().x + w, shape.getPoint1().y - ((Text) shape).getFont().getSize()));

        return new Point(s.getPosition().x + s.getSize().x, s.getPosition().y
                + s.getSize().y);
    }

    @Override
    public void drawShape(Graphics g) {
        g.setFont(((Text) shape).getFont());
        int w = g.getFontMetrics().stringWidth(((Text) shape).getText());
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g.drawString(((Text) shape).getText(), ((Text) shape).getPoint1().x, ((Text) shape).getPoint1().y);
    }
}
