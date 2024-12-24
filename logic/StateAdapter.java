package logic;

import shapes.Shape;

import java.awt.*;
import java.io.File;

public interface StateAdapter {
    String getTextInput(String title);
    void writeImgToFile(File file);
    File getFileToOpen(String description, String extensions);
    File getFileToWrite(String description, String extensions);
    boolean getChoose(String title, String description);
    Dimension getDrawingSize();
}
