package logic.States;

import logic.DrawingController;
import logic.actions.MoveAction;
import shapes.FillableShape;
import shapes.Shape;
import shapes.Text;

import java.awt.*;

public class SelectState extends DrawingState {
    public SelectState(DrawingController c) {
        super(c);
    }

    boolean isMoveStart = false;

    @Override
    public void processPress(Point p, boolean isAdditionalButtonPressed) {
        Shape tmp = controller.getDrawing().getShapeAt(p);
        isMoveStart = false;

        if (!isAdditionalButtonPressed
                && !controller.getSelection().contains(tmp)) {
            controller.getDrawing().emptySelection();
        }

        if ((tmp != null) && (!controller.getSelection().contains(tmp))) {
            controller.setColor(tmp.getColor());

            if ((controller.getSelection().isEmpty())
                    && (tmp instanceof FillableShape)) {
                controller.setFill(((FillableShape) tmp).getFilled());
            }

            if (tmp instanceof Text) {
                controller.setFontSize(((Text) tmp).getFont().getSize());
            }

            controller.getDrawing().addShapeToSelection(tmp);
        }
    }

    @Override
    public void processDrag(int difX, int difY) {
        Point delta = new Point(difX, difY);

        if (!controller.getSelection().isEmpty()) {
            MoveAction action = new MoveAction(controller.getDrawing().getSelection(), delta, controller.getDrawing(), false);
            isMoveStart = true;

            controller.addAction(action);
        }
    }

    @Override
    public void processRelease() {
        if (!controller.getSelection().isEmpty() && isMoveStart) {
            MoveAction action = new MoveAction(controller.getDrawing().getSelection(), new Point(0, 0), controller.getDrawing(), true);

            controller.addAction(action);
        }
    }
}
