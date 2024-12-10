package logic;

import shapes.Shape;

import java.io.File;

public interface StateAdapter {
    void constructionStart(Shape shape);
    void constructionUpdate(Shape shape);
    void constructionEnd(Shape shape);
    String getTextInput(String title);
    void writeImgToFile(File file);
}
