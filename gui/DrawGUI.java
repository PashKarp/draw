package gui;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import gui.Widgets.WidgetFactory;
import gui.Widgets.GUIShapeWidget;
import logic.StateAdapter;
import logic.StateListener;
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
	public class DrawingContainer extends JPanel implements StateAdapter, StateListener {

		private static final long serialVersionUID = 0;

		private ArrayList<GUIShapeWidget> shapesAdapters;

		private WidgetFactory factory;

		public DrawingContainer() {
			super(new GridBagLayout());
			shapesAdapters = new ArrayList<GUIShapeWidget>();
			factory = new WidgetFactory();
		}

		public void setDrawing(VectorDrawing d) {
			this.removeAll();
			setBorder(BorderFactory.createLineBorder(Color.black));
			setBackground(Color.WHITE);

			shapesAdapters.clear();
			setPreferredSize(new Dimension(500, 380));

			if (mouse == null) {
				mouse = new MouseListener(controller);
				this.addMouseListener(mouse);
				this.addMouseMotionListener(mouse);
			}

			d.addDrawingListener(new DrawingObserver());
			pack();
		}

		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			for (GUIShapeWidget s : shapesAdapters) {
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
			GUIShapeWidget adapter = factory.create(shape);

			shapesAdapters.add(adapter);
			repaint();
		}

		private void updateShape(Shape shape) {
			GUIShapeWidget adapter = factory.create(shape);

			shapesAdapters.remove(adapter);
			shapesAdapters.add(adapter);
			repaint();
		}

		private void deleteShape(Shape shape) {
			GUIShapeWidget adapter = factory.create(shape);

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

		@Override
		public void writeImgToFile(File file) {
			try {
				controller.getDrawing().emptySelection();
				BufferedImage bi = this.getImage(); // retrieve image
				ImageIO.write(bi, "png", file);
			}
			catch (IOException e) {
			}
		}

		@Override
		public File getFileToOpen(String description, String extensions) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileFilter filter = new FileNameExtensionFilter(description, extensions);
			fileDialog.addChoosableFileFilter(filter);
			fileDialog.setFileFilter(filter);

			fileDialog.showOpenDialog(null);

			return fileDialog.getSelectedFile();
		}

		@Override
		public File getFileToWrite(String description, String extensions) {
			JFileChooser fileDialog = new JFileChooser();
			fileDialog.setFileSelectionMode(JFileChooser.FILES_ONLY);

			FileFilter filter = new FileNameExtensionFilter(description, extensions);
			fileDialog.addChoosableFileFilter(filter);
			fileDialog.setFileFilter(filter);

			fileDialog.showSaveDialog(null);

			return fileDialog.getSelectedFile();
		}

		@Override
		public boolean getChoose(String title, String description) {
			return JOptionPane.showConfirmDialog(
					null,
					description,
					title,
					JOptionPane.YES_NO_OPTION
			) == JOptionPane.YES_OPTION;
		}

		@Override
		public Dimension getDrawingSize() {
			MainMenu.NewDrawingDialog diag = new MainMenu.NewDrawingDialog();
			return diag.getNewSize();
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
		controller.addStateListener(drawingContainer);
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
