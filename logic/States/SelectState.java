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

    MoveAction moveAction;

    @Override
    public void processPress(Point p, boolean isAdditionalButtonPressed) {
        moveAction = null;

        Shape tmp = controller.getDrawing().getShapeAt(p);

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
        if (moveAction == null) {
            if (!controller.getDrawing().getSelection().isEmpty()) {
                moveAction = new MoveAction(controller.getDrawing().getSelection(), delta, controller.getDrawing());
                controller.addAction(moveAction);
            }
        } else {
            controller.updateUpdatableAction(delta);
        }
    }
}
