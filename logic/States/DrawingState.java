package logic.States;

import logic.DrawingController;
import logic.actions.ColorAction;
import logic.actions.DeleteAction;
import logic.actions.DrawAction;
import logic.actions.FillAction;
import shapes.Shape;

import java.awt.*;

public abstract class DrawingState {
    protected DrawingController controller;

    public DrawingState(DrawingController c) {
        controller = c;
    }

    public void processPress(Point p, boolean isAdditionalButtonPressed) {}
    public void processDrag(int difX, int difY) {}
    public void processRelease() {}
    public void processDelete() {
        DrawAction del = new DeleteAction(controller.getDrawing(), controller.getDrawing().getSelection());
        controller.addAction(del);
    }
    public void processSelectAll() {
        controller.getDrawing().emptySelection();
        controller.getDrawing().addAllShapesToSelection();
    }
    public void clearSelection() {
        controller.getDrawing().emptySelection();
    }
    public void processUpdateColor(Color c) {
        for (Shape s : controller.getDrawing().getSelection()) {
            DrawAction col = new ColorAction(s, c, controller.getDrawing());
            controller.addAction(col);
        }
    }
    public void processUpdateIsFill() {
        DrawAction toggle = new FillAction(controller.getDrawing().getSelection(), controller.getDrawing());
        controller.addAction(toggle);
    }
    public void processUpdateFontSize() {}
    public void processStateChange() {}
}
