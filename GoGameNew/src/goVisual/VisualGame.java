package goVisual;

import GoGame.GoMove;
import main.GoGameLoop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.List;

public class VisualGame extends JPanel {

    private GoGameLoop goBoard;
    private static String BLACK = "Black captured: ";
    private static String WHITE = "White captured: ";
   
    private VisualBoard visualBoard;
    private ControlPanel controlPanel;
    private JPanel capturedPanel;
    private JLabel capturedText;

    public VisualGame(GoGameLoop goBoard) {
        super();
        init(goBoard);
    }

    private void init(GoGameLoop goGameLoop) {
        this.goBoard = goGameLoop;
        this.visualBoard = new VisualBoard(goGameLoop.getGoBoard());
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
        String text = BLACK + goBoard.getGoBoard().getBlackCaptured();
        text += ", " + WHITE + goBoard.getGoBoard().getWhiteCaptured();
        capturedText = new JLabel(text);
        capturedPanel.add(capturedText);
    }

    public void updateGame() {
        this.visualBoard.updateGame();
        updateCaptured();
    }

    public void setListeners(List<GoMove> moves, MouseListener mouseListener) {
        this.visualBoard.setListeners(moves, mouseListener);
        this.controlPanel.setListeners(mouseListener);
    }

    public void removeListeners(List<GoMove> moves, MouseListener mouseListener) {
        this.visualBoard.removeListeners(moves, mouseListener);
        this.controlPanel.removeListener(mouseListener);
    }

}
