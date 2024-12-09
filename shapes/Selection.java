package shapes;

import java.util.ArrayList;
import java.util.Iterator;

public class Selection implements ImmutableSelection {

	private ArrayList<Shape> selected;

	public Selection() {
		selected = new ArrayList<Shape>(0);
	}

	public Selection(ArrayList<Shape> list) {
		selected = list;
	}

	public void add(Shape s) {
		if (!selected.contains(s)) {
			selected.add(s);
		}
	}

	@SuppressWarnings("unchecked")
	public Selection clone() {
		ArrayList<Shape> clone = (ArrayList<Shape>) selected.clone();
		return new Selection(clone);
	}

	public boolean contains(Shape s) {
		return selected.contains(s);
	}

	public void empty() {
		selected.clear();
	}

	public boolean isEmpty() {
		return selected.isEmpty();
	}

	public Iterator<Shape> iterator() {
		return selected.iterator();
	}

	public int nShapes() {
		return selected.size();
	}

	public String toString() {
		String str;
		str = "Selection contents:\n";
		for (Shape s : selected) {
			str += s.toString() + "\n";
		}
		str += "\n";
		return str;
	}
}
