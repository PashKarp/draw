package logic;

import gui.DrawGUI;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import logic.States.*;
import logic.actions.DrawAction;
import shapes.ImmutableSelection;
import shapes.Shape;
import shapes.VectorDrawing;
import logic.actions.UndoManager;

public class DrawingController {

	private VectorDrawing drawing;
	private UndoManager undoManager;

	private DrawGUI gui;
	private Tool tool;

	private boolean fill;
	private Color color;
	private int fontSize;

	private ArrayList<DrawingControllerListener> controllerListeners;
	private ArrayList<StateListener> stateListeners;
	private StateAdapter stateAdapter;

	private HashMap<Tool, DrawingState> states;

	private DrawIO drawIO;

	public DrawingController(DrawGUI g, StateAdapter adapter) {
		drawing = null;
		undoManager = new UndoManager();
		gui = g;
		tool = Tool.LINE;
		stateAdapter = adapter;
		drawIO = new DrawIO(this);

		states = new HashMap<Tool, DrawingState>();

		states.put(Tool.SELECT, new SelectState(this));
		states.put(Tool.CIRCLE, new NewCircleState(this));
		states.put(Tool.LINE, new NewLineState(this));
		states.put(Tool.RECTANGLE, new NewRectangleState(this));
		states.put(Tool.SQUARE, new NewRectangleState(this));
		states.put(Tool.TEXT, new NewTextState(this));

		controllerListeners = new ArrayList<DrawingControllerListener>();
		stateListeners = new ArrayList<StateListener>();
	}

	public VectorDrawing getDrawing() {
		return drawing;
	}

	public ImmutableSelection getSelection() {
		return drawing.getSelection();
	}

	public Tool getTool() {
		return tool;
	}

	public void addAction(DrawAction action) {
		action.execute();
		undoManager.addAction(action);
	}

	public DrawingState getState() {
		return states.get(tool);
	}

	public DrawGUI getGui() {
		return gui;
	}

	public void setColor(Color c) {
		color = c;

		getState().processUpdateColor(color);

		fireColorChanged(getColor());
	}

	public Color getColor() {
		return color;
	}

	public void setFill(boolean f) {
		fill = f;

		getState().processUpdateIsFill();

		fireFillChanged(getFill());
	}

	public boolean getFill() {
		return fill;
	}

	public void setFontSize(int f) {
		fontSize = f;

		fireFontSizeChanged(getFontSize());
	}

	public int getFontSize() {
		return fontSize;
	}

	public StateAdapter getStateAdapter() {
		return stateAdapter;
	}

	public void newDrawing() {
		drawing = new VectorDrawing();
		undoManager = new UndoManager();
		if (gui != null) {
			gui.updateDrawing();
		}
	}

	public void redo() {
		if (this.undoManager.canRedo()) {
			this.undoManager.redo();
		}
	}

	public void setTool(Tool t) {
		this.tool = t;
	}

	public void save() {
		getDrawing().listShapes();
	}

	public void saveAs() {
		File file = stateAdapter.getFileToWrite("Draw files", "draw");

		if (file != null) {
			drawIO.save(file);
		}
	}

	public void open() {
		File file = stateAdapter.getFileToOpen("Draw files", "draw");

		if (file != null) {
			drawIO.open(file);
		}
	}

	public void exportPNG() {
		File file = stateAdapter.getFileToWrite("Portable Network Graphics", "png");

		if (file != null) {
			drawIO.export(file);
		}
	}

	public void createNew() {
		boolean createChosen = true;
		if (drawing.nShapes() > 0) {
			createChosen = stateAdapter.getChoose("Are you sure?", "Are you sure you want to delete your current drawing?");
		}

		if (createChosen) {
			Dimension size = stateAdapter.getDrawingSize();
			if (size != null) {
				newDrawing();
			}
		}
	}

	public void undo() {
		if (this.undoManager.canUndo()) {
			this.undoManager.undo();
		}
	}

	public void addListener(DrawingControllerListener listener) {
		if (! controllerListeners.contains(listener)) {
			controllerListeners.add(listener);
		}
	}

	public void removeListener(DrawingControllerListener listener) {
		controllerListeners.remove(listener);
	}

	private void fireColorChanged(Color color) {
		for (DrawingControllerListener listener : controllerListeners) {
			listener.colorChanged(color);
		}
	}

	private void fireFillChanged(boolean fill) {
		for (DrawingControllerListener listener : controllerListeners) {
			listener.fillChanged(fill);
		}
	}

	private void fireFontSizeChanged(int fontSize) {
		for (DrawingControllerListener listener : controllerListeners) {
			listener.fontSizeChanged(fontSize);
		}
	}

	public void addStateListener(StateListener listener) {
		if (! stateListeners.contains(listener)) {
			stateListeners.add(listener);
		}
	}

	public void removeStateListener(StateListener listener) {
		stateListeners.remove(listener);
	}

	public void fireConstructionStart(Shape s) {
		for (StateListener listener : stateListeners) {
			listener.constructionStart(s);
		}
	}

	public void fireConstructionUpdate(Shape s) {
		for (StateListener listener : stateListeners) {
			listener.constructionUpdate(s);
		}
	}

	public void fireConstructionEnd(Shape s) {
		for (StateListener listener : stateListeners) {
			listener.constructionEnd(s);
		}
	}
}
