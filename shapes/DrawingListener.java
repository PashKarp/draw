package shapes;

import java.util.ArrayList;

public interface DrawingListener {
    void shapeAppended(Shape s);
    void shapeDeleted(Shape s);
    void shapeUpdated(Shape s);
    void shapeAppendedToSelection(Shape s);
    void selectionCleared(ArrayList<Shape> shapes);
}
