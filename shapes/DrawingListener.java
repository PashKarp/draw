package shapes;

public interface DrawingListener {
    void shapeAppended(Shape s);
    void shapeDeleted(Shape s);
    void shapeUpdated(Shape s);
    void shapeAppendedToSelection(Shape s);
    void selectionCleared();
}
