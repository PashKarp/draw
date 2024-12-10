package gui;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import logic.DrawingController;
import logic.Tool;
import shapes.Circle;
import shapes.FillableShape;
import shapes.Line;
import shapes.Rectangle;
import shapes.Shape;
import shapes.Text;

/**
 * MouseListener listens to the mouse events in a drawing and modifies the
 * Drawing through a DrawingController
 * 
 * @author Alex Lagerstedt
 * 
 */

public class MouseListener extends MouseAdapter {

	private DrawingController c;

	private Point startPos;
	private Point lastPos;

	/**
	 * Constructs a new MouseListener
	 * 
	 * @param c
	 *            the DrawingController through which the modifications will be
	 *            done
	 */
	public MouseListener(DrawingController c) {
		this.c = c;
	}

	public void mouseDragged(MouseEvent m) {
		c.getState().processDrag(m.getPoint().x - lastPos.x, m.getPoint().y - lastPos.y);

		lastPos = m.getPoint();
	}

	public void mouseMoved(MouseEvent m) {
		lastPos = m.getPoint();
	}

	public void mousePressed(MouseEvent m) {
		startPos = lastPos;

		c.getState().processPress(startPos, (m.getModifiersEx() & InputEvent.SHIFT_DOWN_MASK) != 0);
	}

	public void mouseReleased(MouseEvent m) {
		c.getState().processRelease();
	}

}
