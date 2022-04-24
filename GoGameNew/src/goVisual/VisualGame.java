package goVisual;

import GoGame.GoBoard;
import GoGame.GoMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.List;

public class VisualGame extends JPanel {

    private GoBoard goBoard;
    private static String BLACK = "Black captured: ";
    private static String WHITE = "White captured: ";
   
    private VisualBoard visualBoard;
    private ControlPanel controlPanel;
    private JPanel capturedPanel;
    private JLabel capturedText;

    public VisualGame(GoBoard goBoard) {
        super();
        init(goBoard);
    }

    private void init(GoBoard goBoard) {
        this.goBoard = goBoard;
        this.visualBoard = new VisualBoard(goBoard);
        this.controlPanel = new ControlPanel();
        this.capturedPanel = new JPanel();
        

        this.setLayout(new BorderLayout());
        this.add(visualBoard, BorderLayout.NORTH);
        this.add(capturedPanel, BorderLayout.CENTER);
        this.add(controlPanel, BorderLayout.SOUTH);
        updateCaptured();
    }

    public JPanel createCaptures(){
        JPanel capturedPanel = new JPanel();
        updateCaptured();
        return capturedPanel;
    }

    private void updateCaptured(){
        if(capturedText != null)
            capturedPanel.remove(capturedText);
        String text = BLACK + goBoard.getBlackCaptured();
        text += ", " + WHITE + goBoard.getWhiteCaptured();
        capturedText = new JLabel(text);
        capturedPanel.add(capturedText);
    }

    public void updateGame() {
        this.visualBoard.updateGame();
        updateCaptured();
    }

    public void setListeners(List<GoMove> moves, MouseListener mouseListener) {
        this.visualBoard.setListeners(moves, mouseListener);
        this.controlPanel.setListener(mouseListener);
    }

    public void removeListeners(List<GoMove> moves, MouseListener mouseListener) {
        this.visualBoard.removeListeners(moves, mouseListener);
        this.controlPanel.removeListener(mouseListener);
    }
}
