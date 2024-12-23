package gui;

import gui.MainMenu.NewDrawingDialog;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import logic.DrawIO;
import logic.DrawingController;

/**
 * Listens to logic.actions from the buttons in a menu and modifies the Drawing
 * through a DrawingController
 * 
 * @author Alex Lagerstedt
 * 
 */
public class MenuListener implements ActionListener {

	DrawingController controller;

	public MenuListener(DrawingController c) {
		this.controller = c;
	}

	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();

		if (cmd.equals("Quit")) {
			System.exit(0);
		}

		else if (cmd.equals("Save")) {
			controller.save();
		}

		else if (cmd.equals("Undo")) {
			controller.undo();
		}

		else if (cmd.equals("Redo")) {
			controller.redo();
		}

		else if (cmd.equals("Select all")) {
			controller.getState().processSelectAll();
		}

		else if (cmd.equals("Clear selection")) {
			controller.getDrawing().emptySelection();
		}

		else if (cmd.equals("Delete")) {
			controller.getState().processDelete();
		}

		else if (cmd.equals("Open")) {
			controller.open();
		}

		else if (cmd.equals("Save as")) {
			controller.saveAs();
		}

		else if (cmd.equals("Export PNG")) {
			controller.exportPNG();
		}

		else if (cmd.equals("New")) {
			controller.createNew();
		}

		else {
			JOptionPane.showMessageDialog(null, "Not implemented.",
					"Not implemented", JOptionPane.ERROR_MESSAGE);
		}
	}
}
