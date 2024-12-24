package logic;

import shapes.Shape;

import java.awt.*;

public interface StateListener {
    void constructionStart(shapes.Shape shape);
    void constructionUpdate(shapes.Shape shape);
    void constructionEnd(Shape shape);
}
