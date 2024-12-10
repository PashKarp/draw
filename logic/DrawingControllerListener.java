package logic;

import java.awt.*;

public interface DrawingControllerListener {
    void colorChanged(Color color);
    void fillChanged(boolean fill);
    void fontSizeChanged(int fontSize);
}
