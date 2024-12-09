package shapes;

public interface ImmutableSelection extends Iterable<Shape> {
    boolean contains(Shape s);
    boolean isEmpty();
    int nShapes();
    String toString();
}
