package logic;

import gui.DrawGUI;

import java.awt.Color;
import java.awt.Point;

import shapes.ImmutableSelection;
import shapes.VectorDrawing;
import shapes.Shape;
import actions.AddAction;
import actions.ColorAction;
import actions.DeleteAction;
import actions.DrawAction;
import actions.FillAction;
import actions.MoveAction;
import actions.UndoManager;

public class DrawingController {

	private VectorDrawing drawing;
	private UndoManager undoManager;

	private DrawGUI gui;
	private Tool tool;
	private MoveAction currentDrawAction;
	private boolean isActionStart = false;

	public DrawingController(DrawGUI g) {
		drawing = null;
		undoManager = new UndoManager();
		gui = g;
		tool = Tool.LINE;
	}

	public void addShape(Shape s) {
		DrawAction add = new AddAction(drawing, s);
		add.execute();
		undoManager.addAction(add);
		isActionStart = false;
	}

	public void colorSelectedShapes(Color c) {
		for (Shape s : drawing.getSelection()) {
			DrawAction col = new ColorAction(s, c, drawing);
			col.execute();
			undoManager.addAction(col);
		}
	}

	public void deleteSelectedShapes() {
		DrawAction del = new DeleteAction(drawing, drawing.getSelection());
		del.execute();
		undoManager.addAction(del);
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

	public DrawGUI getGui() {
		return gui;
	}

	public void moveSelectedShapes(Point movement) {
//		if (!selection.isEmpty()) {
//			if (currentDrawAction == null) {
//				currentDrawAction = new MoveAction(selection, movement);
//			} else {
//				currentDrawAction.undo();
//				currentDrawAction = currentDrawAction.update(movement);
//			}
//			currentDrawAction.execute();
//			drawing.repaint();
//		}
	}

	public void moveUpdate(Point movement) {
		if (isActionStart) {
			if (!drawing.getSelection().isEmpty() && this.getTool() == Tool.SELECT) {
				isActionStart = false;
				DrawAction action = new MoveAction(drawing.getSelection(), movement, drawing);
				undoManager.addAction(action);
				action.execute();
			}
		} else {
			undoManager.updateMoveUpdatableAction(movement);
		}
	}

	public void newDrawing() {
		drawing = new VectorDrawing();
		if (gui != null) {
			gui.updateDrawing();
		}
	}

	public void recordMovement() {
		if (!drawing.getSelection().isEmpty()) {
			undoManager.addAction(currentDrawAction);
			currentDrawAction = null;
		}
	}

	public void resetMovement() {
		currentDrawAction = null;
	}

	public void redo() {
		if (this.undoManager.canRedo()) {
			this.undoManager.redo();
		}
	}

	public void selectAll() {
		drawing.emptySelection();
		for (Shape sh : drawing) {
			drawing.addShapeToSelection(sh);
		}

	}

	public void setTool(Tool t) {
		this.tool = t;
	}

	public void toggleFilled() {
		DrawAction toggle = new FillAction(drawing.getSelection(), drawing);
		toggle.execute();
		undoManager.addAction(toggle);
	}

	public void undo() {
		if (this.undoManager.canUndo()) {
			this.undoManager.undo();
		}
	}

	public void setIsActionStart(boolean isActionStart) {
		this.isActionStart = isActionStart;
	}
}
