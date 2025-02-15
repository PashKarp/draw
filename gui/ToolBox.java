package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import logic.DrawingController;
import logic.DrawingControllerListener;
import logic.Tool;

public class ToolBox extends JToolBar implements ActionListener,
		ChangeListener, ItemListener, DrawingControllerListener {

	@Override
	public void colorChanged(Color color) {
		colorbutton.setBackground(this.c.getColor());
	}

	@Override
	public void fillChanged(boolean fill) {
		fillCheckBox.setSelected(this.c.getFill());
	}

	@Override
	public void fontSizeChanged(int fontSize) {
		fontSpinner.setValue(c.getFontSize());
	}

	class ColorDialog extends JDialog {
		private static final long serialVersionUID = 0;

		private JColorChooser colorChooser = new JColorChooser();
		private JButton okButton = new JButton("OK");
		private JButton cancelButton = new JButton("Cancel");

		public ColorDialog(final ToolBox tb) {
			setTitle("Color Dialog");
			setLayout(new BorderLayout());
			add(colorChooser, BorderLayout.NORTH);
			JPanel jp = new JPanel(new FlowLayout(FlowLayout.RIGHT));
			jp.add(okButton);
			jp.add(cancelButton);
			add(jp, BorderLayout.SOUTH);
			pack();
			setVisible(true);
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					setVisible(false);
					tb.setColor(colorChooser.getColor());
				}
			});
			cancelButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent a) {
					setVisible(false);
				}
			});
		}
	}

	private static final long serialVersionUID = 1L;

	private DrawingController c;
	private ButtonGroup buttons;
	private JToggleButton select;
	private JToggleButton line;
	private JToggleButton rectangle;
	private JToggleButton circle;
	private JToggleButton text;

	private JButton colorbutton;

	private JCheckBox fillCheckBox;
	private JSpinner fontSpinner;

	public ToolBox(DrawingController c) {
		super("Tools", VERTICAL);
		this.c = c;
		// tool = Tool.LINE;
		this.c.setColor(Color.BLACK);

		select = new JToggleButton(new ImageIcon("img/cursor.png"));
		select.setToolTipText("Select and move shapes");
		line = new JToggleButton(new ImageIcon("img/line.png"));
		line.setToolTipText("Draw lines");
		rectangle = new JToggleButton(new ImageIcon("img/rectangle.png"));
		rectangle.setToolTipText("Draw squares and rectangles");
		circle = new JToggleButton(new ImageIcon("img/circle.png"));
		circle.setToolTipText("Draw circles and ellipses");
		text = new JToggleButton(new ImageIcon("img/text.png"));
		text.setToolTipText("Create text");

		colorbutton = new JButton("");
		colorbutton.setPreferredSize(new Dimension(44, 44));
		colorbutton.setMaximumSize(new Dimension(44, 44));
		colorbutton.setBackground(Color.BLACK);
		colorbutton.setForeground(Color.BLACK);

		colorbutton.setToolTipText("Click to change drawing color");

		fillCheckBox = new JCheckBox();
		fillCheckBox.addItemListener(this);

		fontSpinner = new JSpinner(new SpinnerNumberModel(12, 6, 96, 1));
		fontSpinner.setPreferredSize(new Dimension(0, 20));
		fontSpinner.setMaximumSize(new Dimension(0, 20));
		fontSpinner.setMinimumSize(new Dimension(0, 20));
		fontSpinner.addChangeListener(this);
		setFontSize(12);

		select.addActionListener(this);
		line.addActionListener(this);
		rectangle.addActionListener(this);
		circle.addActionListener(this);
		colorbutton.addActionListener(this);
		text.addActionListener(this);

		add(Box.createRigidArea(new Dimension(10, 10)));
		add(new JLabel("Tools"));
		add(Box.createRigidArea(new Dimension(10, 10)));
		add(select);
		add(line);
		add(rectangle);
		add(circle);
		add(text);
		add(Box.createRigidArea(new Dimension(10, 10)));
		add(new JLabel("Fill"));

		add(fillCheckBox);

		add(Box.createRigidArea(new Dimension(10, 10)));
		add(new JLabel("Color"));
		add(Box.createRigidArea(new Dimension(10, 10)));
		add(colorbutton);

		add(Box.createRigidArea(new Dimension(10, 10)));
		add(new JLabel("Font"));
		add(Box.createRigidArea(new Dimension(10, 10)));

		JPanel jp = new JPanel(new BorderLayout());
		jp.add(fontSpinner, BorderLayout.NORTH);
		add(jp);

		buttons = new ButtonGroup();
		buttons.add(select);
		buttons.add(line);
		buttons.add(circle);
		buttons.add(rectangle);
		buttons.add(text);

		c.addListener(this);
	}

	/**
	 * Changes the active tool when a button is pressed.
	 */
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source.equals(select)) {
			c.setTool(Tool.SELECT);
		}
		else if (!source.equals(colorbutton)) {
			c.getDrawing().emptySelection();
		}

		if (source.equals(circle)) {
			c.setTool(Tool.CIRCLE);
		}

		if (source.equals(line)) {
			c.setTool(Tool.LINE);
			fillCheckBox.setEnabled(false);
		}
		else {
			fillCheckBox.setEnabled(true);
		}

		if (source.equals(rectangle)) {
			c.setTool(Tool.RECTANGLE);
		}
		else if (source.equals(text)) {
			c.setTool(Tool.TEXT);
		}
		else if (source.equals(colorbutton)) {
			new ColorDialog(this);
		}
	}

	public Color getColor() {
		return c.getColor();
	}

	public boolean getFill() {
		return c.getFill();
	}

	public int getFontSize() {
		return c.getFontSize();
	}

	/**
	 * Changes the fill status of all Selected Shapes when fill check box is
	 * clicked.
	 */
	public void itemStateChanged(ItemEvent e) {
		if (e.getStateChange() == ItemEvent.DESELECTED) {
			c.setFill(false);
		}
		else {
			c.setFill(true);
		}
	}

	public void setColor(Color c) {
		this.c.setColor(c);
	}

	public void setFill(boolean f) {
		this.c.setFill(f);
	}

	public void setFontSize(int size) {
		c.setFontSize(size);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();

		if (source.equals(fontSpinner)) {
			setFontSize((Integer) ((JSpinner) source).getValue());
		}
	}

}
