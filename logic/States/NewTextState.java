package logic.States;

import logic.DrawingController;
import logic.actions.AddAction;
import logic.actions.DrawAction;
import shapes.ShapeType;
import shapes.Text;

import java.awt.*;

public class NewTextState extends DrawingState {
    public NewTextState(DrawingController c) {
        super(c);
    }

    public void processPress(Point p, boolean isAdditionalButtonPressed) {
        String text = controller.getStateAdapter().getTextInput("Text to be inserted:");

        Text newShape = (Text) controller.getDrawing().getShapePrototype(ShapeType.Text);
        newShape = (Text) newShape.setColor(controller.getColor());
        newShape = (Text) newShape.setPoint1(p);
        newShape = newShape.setText(text);
        newShape = newShape.setFont(controller.getFontSize());

        DrawAction add = new AddAction(controller.getDrawing(), newShape);
        controller.addAction(add);
    }
}
