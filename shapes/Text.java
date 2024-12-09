package shapes;

import java.awt.*;

import javax.swing.JOptionPane;

public class Text extends Shape {

	private String text;
	private Font font;

	/**
	 * Constructs a new Text shape and asks the user for the text with a dialog
	 * box
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param fontSize
	 *            font size
	 */
	public Text(int x, int y, int fontSize) {
		super(new Point(x, y));
		font = new Font(null, Font.PLAIN, fontSize);
		text = JOptionPane.showInputDialog("Text to be inserted:");
		if ((text == null) || (text.length() == 0)) {
			throw new IllegalArgumentException("Empty text");
		}
	}

	/**
	 * Constructs a new Text shape with the given string as the text.
	 * 
	 * @param x
	 *            x-coordinate
	 * @param y
	 *            y-coordinate
	 * @param fontSize
	 *            font size
	 * @param str
	 *            the text for the shape
	 */
	public Text(int x, int y, int fontSize, String str) {
		super(new Point(x, y));
		font = new Font(null, Font.PLAIN, fontSize);
		text = str;

	}

	public String getText() {
		return text;
	}

	public Font getFont() {
		return font;
	}

	@Override
	public ShapeType getType() {
		return ShapeType.Text;
	}

	public String toString() {
		return "text;" + super.toString() + ";" + font.getSize() + ";"
				+ text.replace(';', '?');
	}

	public Text clone() {
		Text clone = (Text) super.clone();

		clone.font = font;
		clone.text = text;

		return clone;
	}
}
