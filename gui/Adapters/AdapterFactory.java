package gui.Adapters;

import shapes.*;

public class AdapterFactory {
    static public ShapeAdapter create(Shape s) {
        switch (s.getType()) {
            case Line -> {
                return new LineAdapter((Line) s);
            }
            case Text -> {
                return new TextAdapter((Text) s);
            }
            case Circle -> {
                return new CircleAdapter((Circle) s);
            }
            case Rectangle -> {
                return new RectangleAdapter((Rectangle) s);
            }
        }

        return null;
    }
}
