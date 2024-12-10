package logic;

import gui.DrawGUI;

import java.awt.Color;
import java.awt.Point;
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

	private ArrayList<DrawingControllerListener> listeners;
	private StateAdapter stateAdapter;

	private HashMap<Tool, DrawingState> states;

	public DrawingController(DrawGUI g, StateAdapter adapter) {
		drawing = null;
		undoManager = new UndoManager();
		gui = g;
		tool = Tool.LINE;
		stateAdapter = adapter;

		states = new HashMap<Tool, DrawingState>();

		states.put(Tool.SELECT, new SelectState(this));
		states.put(Tool.CIRCLE, new NewCircleState(this));
		states.put(Tool.LINE, new NewLineState(this));
		states.put(Tool.RECTANGLE, new NewRectangleState(this));
		states.put(Tool.SQUARE, new NewRectangleState(this));
		states.put(Tool.TEXT, new NewTextState(this));

		listeners = new ArrayList<DrawingControllerListener>();
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

	public void updateUpdatableAction(Point movement) {
		undoManager.updateMoveUpdatableAction(movement);
	}

	public void newDrawing() {
		drawing = new VectorDrawing();
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


	public void undo() {
		if (this.undoManager.canUndo()) {
			this.undoManager.undo();
		}
	}

	public void addListener(DrawingControllerListener listener) {
		if (! listeners.contains(listener)) {
			listeners.add(listener);
		}
	}

	public void removeListener(DrawingControllerListener listener) {
		listeners.remove(listener);
	}

	private void fireColorChanged(Color color) {
		for (DrawingControllerListener listener : listeners) {
			listener.colorChanged(color);
		}
	}

	private void fireFillChanged(boolean fill) {
		for (DrawingControllerListener listener : listeners) {
			listener.fillChanged(fill);
		}
	}

	private void fireFontSizeChanged(int fontSize) {
		for (DrawingControllerListener listener : listeners) {
			listener.fontSizeChanged(fontSize);
		}
	}
}
