package logic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import gui.DrawGUI;
import shapes.*;

public class DrawIO {

	public void export(File f, DrawingController c) {
		c.getStateAdapter().writeImgToFile(f);
	}

	public Point getPoint(String str) {
		String[] p = str.split(",");

		return new Point(Integer.parseInt(p[0].trim()), Integer.parseInt(p[1]
				.trim()));

	}

	public void open(File f, DrawingController c) {
		int lineNumber = 1;
		try {
			BufferedReader in = new BufferedReader(new FileReader(f));
			String str;

			//Point p = getPoint(in.readLine());
			c.newDrawing();

			while ((str = in.readLine()) != null) {
				try {
					lineNumber++;
					if (str.length() == 0) {
						continue;
					}

					String[] parts = str.split(";");

					Point p1 = getPoint(parts[1]);
					Point p2 = getPoint(parts[2]);
					Shape sh = null;
					parts[0] = parts[0].trim();

					if (parts[0].equals("rect")) {
						boolean fill = Integer.parseInt(parts[4].trim()) == 0 ? false
								: true;

						sh = c.getDrawing().getShapePrototype(ShapeType.Rectangle);

						sh = ((Rectangle) sh).setFilled(fill);
					}
					else if (parts[0].equals("circ")) {
						boolean fill = Integer.parseInt(parts[4].trim()) == 0 ? false
								: true;

						sh = c.getDrawing().getShapePrototype(ShapeType.Circle);

						sh = ((Circle) sh).setFilled(fill);
					}
					else if (parts[0].equals("line")) {
						sh = c.getDrawing().getShapePrototype(ShapeType.Line);
					}
					else if (parts[0].equals("text")) {
						int fontSize = Integer.parseInt(parts[4].trim());

						sh = c.getDrawing().getShapePrototype(ShapeType.Text);
						sh = ((Text) sh).setFont(fontSize);
						sh = ((Text) sh).setText(parts[5]);
					}
					else {
						throw new ArrayIndexOutOfBoundsException();
					}

					if (sh != null) {
						sh = sh.setPoint1(p1);
						sh = sh.setPoint2(p2);
						sh = sh.setColor(new Color(Integer.parseInt(parts[3]
										.trim())));
						c.getDrawing().insertShape(sh);
					}
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Could not read line " + lineNumber
							+ " in file \"" + f + "\"");
				}
				catch (NumberFormatException e) {
					System.out.println("Could not read line " + lineNumber
							+ " in file \"" + f + "\"");
				}

			}

			in.close();
		}
		catch (IOException e) {
			e.printStackTrace(System.out);
		}
	}

	public void save(File f, DrawingController c) {
		VectorDrawing d = c.getDrawing();

		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(f));

			for (Shape s : c.getDrawing()) {
				out.write(s.toString() + "\n");
			}
			out.close();

		}
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Could not save the drawing.",
					"Error", JOptionPane.ERROR_MESSAGE);
		}

	}
}
