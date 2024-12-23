package gui.Widgets;

import shapes.*;

public class WidgetFactory extends ShapeWidgetFactory {
    @Override
    public GUIShapeWidget create(Shape s) {
        return (GUIShapeWidget) super.create(s);
    }

    @Override
    public ShapeWidget createLineWidget(Line s) {
        return new LineWidget(s);
    }

    @Override
    public ShapeWidget createTextWidget(Text s) {
        return new TextWidget(s);
    }

    @Override
    public ShapeWidget createCircleWidget(Circle s) {
        return new CircleWidget(s);
    }

    @Override
    public ShapeWidget createRectangleWidget(Rectangle s) {
        return new RectangleWidget(s);
    }
}
