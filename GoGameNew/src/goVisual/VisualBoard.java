package goVisual;

import GoGame.GoBoard;
import GoGame.GoMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.List;

public class VisualBoard extends JLayeredPane {
    private GoBoard goBoard;
    private GoBackgroundPanel background;
    private GoPiecePanel pieces;

    public VisualBoard(GoBoard goBoard) {
        super();
        this.goBoard = goBoard;
        this.background = new GoBackgroundPanel(goBoard.getSize());
        this.pieces = new GoPiecePanel(goBoard);

        this.add(background, 1);
        this.add(pieces, 2);

        this.setLayer(background, 1);
        this.setLayer(pieces, 2);

        init();
    }

    private void init() {
        background.reset(goBoard.getSize());
        pieces.reset(goBoard);

        Dimension size = new Dimension(background.getWidth(),
                this.background.getHeight());

        this.setSize(size);
        this.setPreferredSize(size);

        this.setVisible(true);
        this.setOpaque(true);
    }

    public boolean setListeners(List<GoMove> moves, MouseListener mouseListener) {
        if (this.pieces != null) {
            return pieces.setListeners(moves, mouseListener);
        }
        return false;
    }

    public boolean removeListeners(List<GoMove> moves, MouseListener mouseListener) {
        if (this.pieces != null) {
            return pieces.removeListeners(moves, mouseListener);
        }
        return false;
    }

    public void updateGame() {
        for (int i = 0; i < goBoard.getSize(); i++)
            for (int j = 0; j < goBoard.getSize(); j++) {
                pieces.setTool(i, j, goBoard.getTool(i, j));
            }
    }

}
