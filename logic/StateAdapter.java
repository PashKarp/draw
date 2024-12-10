package logic;

import shapes.Shape;

public interface StateAdapter {
    void constructionStart(Shape shape);
    void constructionUpdate(Shape shape);
    void constructionEnd(Shape shape);
    String getTextInput(String title);
}
