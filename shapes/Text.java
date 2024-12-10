package shapes;

import java.awt.*;

import javax.swing.*;

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

		calculatePoint2();
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

		calculatePoint2();
	}

	public String getText() {
		return text;
	}

	public Font getFont() {
		return font;
	}

	public Text setFont(int fontSize) {
		Text clone = clone();
		clone.font = new Font(null, Font.PLAIN, fontSize);
		clone.calculatePoint2();
		return clone;
	}

	public Text setText(String text) {
		Text clone = clone();
		clone.text = text;
		clone.calculatePoint2();
		return clone;
	}

	private void calculatePoint2() {
		int w = (int) (this.getFont().getSize() * text.length() * 0.54);
		point2 = new Point(this.getPoint1().x + w, this.getPoint1().y - this.getFont().getSize());
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
