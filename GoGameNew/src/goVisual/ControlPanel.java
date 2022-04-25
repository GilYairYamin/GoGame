package goVisual;

import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JPanel;

public class ControlPanel extends JPanel
{
	private JButton territoryButton;
	private JButton undoButton;
	private JButton passButton;

	public ControlPanel()
	{
		super();
		this.territoryButton = makeTerritoryButton();
		this.undoButton = makeUndoButton();
		this.passButton = makePassButton();

		this.add(territoryButton);
		this.add(undoButton);
		this.add(passButton);

		this.setVisible(true);
		this.setOpaque(true);
	}

	private JButton makeUndoButton()
	{
		JButton undoButton = new JButton("Undo");
		return undoButton;
	}

	private JButton makeTerritoryButton()
	{
		JButton territoryButton = new JButton("Territory check");
		return territoryButton;
	}

	private JButton makePassButton()
	{
		JButton passButton = new JButton("Pass");
		return passButton;
	}

	public void setListeners(MouseListener listener)
	{
		passButton.addMouseListener(listener);
		undoButton.addMouseListener(listener);
	}

	public void removeListener(MouseListener listener)
	{
		passButton.removeMouseListener(listener);
		undoButton.removeMouseListener(listener);
	}
}
