package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;

import gui.Adapters.AdapterFactory;
import gui.Adapters.ShapeAdapter;
import logic.StateAdapter;
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
	public class DrawingContainer extends JPanel implements StateAdapter {

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
			mouse = new MouseListener(controller);
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

		private void appendShape(Shape shape) {
			ShapeAdapter adapter = AdapterFactory.create(shape);

			shapesAdapters.add(adapter);
			repaint();
		}

		private void updateShape(Shape shape) {
			ShapeAdapter adapter = AdapterFactory.create(shape);

			shapesAdapters.remove(adapter);
			shapesAdapters.add(adapter);
			repaint();
		}

		private void deleteShape(Shape shape) {
			ShapeAdapter adapter = AdapterFactory.create(shape);

			shapesAdapters.remove(adapter);
			repaint();
		}

		@Override
		public void constructionStart(Shape shape) {
			appendShape(shape);
		}

		@Override
		public void constructionUpdate(Shape shape) {
			updateShape(shape);
		}

		@Override
		public void constructionEnd(Shape shape) {
			deleteShape(shape);
		}

		@Override
		public String getTextInput(String title) {
			return JOptionPane.showInputDialog(title);
		}

		private class DrawingObserver implements DrawingListener {

			@Override
			public void shapeAppended(Shape s) {
				appendShape(s);
			}

			@Override
			public void shapeDeleted(Shape s) {
				deleteShape(s);
			}

			@Override
			public void shapeUpdated(Shape s) {
				updateShape(s);
			}

			@Override
			public void shapeAppendedToSelection(Shape s) {
				updateShape(s);
			}

			@Override
			public void selectionCleared(ArrayList<Shape> shapes) {
				for (Shape s : shapes) {
					updateShape(s);
				}
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

		controller = new DrawingController(this, drawingContainer);
		controller.newDrawing();
		tools = new ToolBox(controller);

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
