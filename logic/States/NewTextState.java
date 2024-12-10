package logic.States;

import logic.DrawingController;
import logic.actions.AddAction;
import logic.actions.DrawAction;
import shapes.Shape;
import shapes.Text;

import java.awt.*;

public class NewTextState extends DrawingState {
    public NewTextState(DrawingController c) {
        super(c);
    }

    public void processPress(Point p, boolean isAdditionalButtonPressed) {
        String text = controller.getStateAdapter().getTextInput("Text to be inserted:");
        Shape newShape = new Text(p.x, p.y, controller.getFontSize(), text);
        newShape = newShape.setColor(controller.getColor());

        DrawAction add = new AddAction(controller.getDrawing(), newShape);
        controller.addAction(add);
    }
}
