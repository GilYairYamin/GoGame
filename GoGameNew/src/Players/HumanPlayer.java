package Players;

import GoGame.GoBoard;
import GoGame.GoMove;
import GoGame.Tool;
import goVisual.ListenerLabel;
import goVisual.VisualGame;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class HumanPlayer extends GoPlayer implements MouseListener {

    public static final int DEFAULT_SLEEP_TIME = 50;
    private GoMove wantedMove;

    public HumanPlayer(GoBoard game, VisualGame visualGame, Tool tool) {
        super(game, visualGame, tool);
        wantedMove = null;
    }

    @Override
    public GoMove getWantedMove() {
        List<GoMove> possibleMoves = this.board.getPossibleMoves(tool);

        this.wantedMove = null;
        this.visualGame.setListeners(possibleMoves, this);
        while (this.wantedMove == null) {
            try {
                Thread.sleep(DEFAULT_SLEEP_TIME);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        this.visualGame.removeListeners(possibleMoves, this);
        return wantedMove;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof ListenerLabel){
            ListenerLabel label = (ListenerLabel) e.getSource();
            int row = label.getRow();
            int col = label.getCol();
            this.wantedMove = new GoMove(row, col, tool);
        }
        if(e.getSource() instanceof JButton){
            JButton b = (JButton) e.getSource();
            if(b.getText().equals("Pass"))
                this.wantedMove = new GoMove(-1, -1, tool);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof ListenerLabel){
            ListenerLabel label = (ListenerLabel) e.getSource();
            label.setIsMouseOn(tool);
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof ListenerLabel){
            ListenerLabel label = (ListenerLabel) e.getSource();
            label.setIsMouseOff();
        }
    }
}
