package shapes;

public abstract class ShapeWidgetFactory {
    public ShapeWidget create(Shape s) {
        return switch (s.getType()) {
            case Text -> createTextWidget((Text) s);
            case Line -> createLineWidget((Line) s);
            case Circle -> createCircleWidget((Circle) s);
            case Rectangle -> createRectangleWidget((Rectangle) s);
        };
    }

    public abstract ShapeWidget createLineWidget(Line s);
    public abstract ShapeWidget createTextWidget(Text s);
    public abstract ShapeWidget createCircleWidget(Circle s);
    public abstract ShapeWidget createRectangleWidget(Rectangle s);
}
