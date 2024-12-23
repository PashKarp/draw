package gui.Widgets;

import shapes.Text;

import java.awt.*;

public class TextWidget extends GUIShapeWidget {
    public TextWidget(Text shape) {
        super(shape);
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
