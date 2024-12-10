package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import gui.Adapters.AdapterFactory;
import gui.Adapters.ShapeAdapter;
import shapes.DrawingListener;
import shapes.Shape;
import shapes.VectorDrawing;
import logic.DrawingController;

/**
 * Graphical user interface for the Drawing editor "Draw"
 * 
 * @author Alex Lagerstedt
 * 
 */

public class DrawGUI extends JFrame {

	/**
	 * A simple container that contains a Drawing instance and keeps it
	 * centered.
	 * 
	 * @author Alex Lagerstedt
	 * 
	 */
	public class DrawingContainer extends JPanel {

		private static final long serialVersionUID = 0;

		private ArrayList<ShapeAdapter> shapesAdapters;

		public DrawingContainer() {
			super(new GridBagLayout());
			shapesAdapters = new ArrayList<ShapeAdapter>();
		}

		public void setDrawing(VectorDrawing d) {
			this.removeAll();
			setBorder(BorderFactory.createLineBorder(Color.black));
			setBackground(Color.WHITE);
			mouse = new MouseListener(controller, tools);
			shapesAdapters.clear();
			this.addMouseListener(mouse);
			this.addMouseMotionListener(mouse);
			setPreferredSize(new Dimension(500, 380));

			d.addDrawingListener(new DrawingObserver());
			pack();
		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			for (ShapeAdapter s : shapesAdapters) {
				s.draw(g);
			}
		}

		public BufferedImage getImage() {

			BufferedImage bi = new BufferedImage(getPreferredSize().width,
					getPreferredSize().height, BufferedImage.TYPE_INT_RGB);
			Graphics g = bi.createGraphics();
			this.print(g);
			return bi;
		}

		private class DrawingObserver implements DrawingListener {

			@Override
			public void shapeAppended(Shape s) {
				ShapeAdapter adapter = AdapterFactory.create(s);

				shapesAdapters.add(adapter);
				repaint();
			}

			@Override
			public void shapeDeleted(Shape s) {
				ShapeAdapter adapter = AdapterFactory.create(s);

				shapesAdapters.remove(adapter);
				repaint();
			}

			@Override
			public void shapeUpdated(Shape s) {
				ShapeAdapter adapter = AdapterFactory.create(s);

				shapesAdapters.remove(adapter);
				shapesAdapters.add(adapter);
				repaint();
			}

			@Override
			public void shapeAppendedToSelection(Shape s) {
				ShapeAdapter adapter = AdapterFactory.create(s);

				shapesAdapters.remove(adapter);
				shapesAdapters.add(adapter);
				repaint();
			}

			@Override
			public void selectionCleared(ArrayList<Shape> shapes) {
				for (Shape s : shapes) {
					ShapeAdapter adapter = AdapterFactory.create(s);

					shapesAdapters.remove(adapter);
					shapesAdapters.add(adapter);
				}

				repaint();
			}
		}
	}

	public class StatusBar extends JLabel {

		private static final long serialVersionUID = 0;

		public StatusBar() {
			super();
			super.setPreferredSize(new Dimension(100, 16));
			setMessage("Ready");
		}

		public void setMessage(String message) {
			setText(" " + message);
		}
	}

	private DrawingController controller;
	private DrawingContainer drawingContainer;
	private MouseListener mouse;
	private ToolBox tools;
	private JScrollPane scrollpane;

	// private StatusBar statusBar;

	private static final long serialVersionUID = 0;

	public static void main(String[] args) {

		new DrawGUI();

	}

	/**
	 * Constructs a new graphical user interface for the program and shows it.
	 */
	public DrawGUI() {

		this.setTitle("Draw 0.2");
		setIconImage(Toolkit.getDefaultToolkit().getImage("img/logo.png"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		drawingContainer = new DrawingContainer();
		scrollpane = new JScrollPane(drawingContainer);

		controller = new DrawingController(this);
		tools = new ToolBox(controller);
		controller.newDrawing();

		// statusBar = new StatusBar();

		getContentPane().add(tools, BorderLayout.WEST);
		getContentPane().add(scrollpane, BorderLayout.CENTER);
		// getContentPane().add(statusBar, BorderLayout.SOUTH);

		MenuListener mainMenuListener = new MenuListener(controller);
		JMenuBar mainMenu = new MainMenu(mainMenuListener);
		this.setJMenuBar(mainMenu);

		pack();
		setVisible(true);

	}

	/**
	 * Updates the GUI to show the Drawing instance that is currently controlled
	 * by the DrawingController.
	 */
	public void updateDrawing() {

		drawingContainer.setDrawing(controller.getDrawing());
		scrollpane.setPreferredSize(new Dimension(drawingContainer
				.getPreferredSize().width + 100, drawingContainer
				.getPreferredSize().height + 100));
		pack();
		repaint();
	}

	public DrawingContainer getDrawingContainer() {
		return drawingContainer;
	}
}
