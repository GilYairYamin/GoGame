package goVisual;

import GoGame.GoBoard;
import GoGame.GoMove;
import GoGame.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.List;

public class GoPiecePanel extends JPanel {
    private ListenerLabel[][] labels;
    private int size;

    private static final int PIC_WIDTH = 25;
    private static final int PIC_HEIGHT = 25;

    public GoPiecePanel(GoBoard goBoard) {
        super();
        this.init(goBoard);
    }

    private void init(GoBoard goBoard) {
        this.size = goBoard.getSize();

        this.setLayout(new GridLayout(size, size));
        resetLabels(goBoard);

        this.setVisible(true);
        this.setOpaque(false);
    }

    private void resetLabels(GoBoard goBoard) {
        this.labels = new ListenerLabel[size][size];
        this.removeAll();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                this.labels[row][col] =
                        new ListenerLabel(goBoard.getTool(row, col), ListenerLabel.PUT, row, col);
                this.labels[row][col].setSize(PIC_WIDTH, PIC_HEIGHT);
                this.labels[row][col].setOpaque(false);
                this.labels[row][col].setVisible(true);
                this.add(labels[row][col]);
            }
        }
        this.setSize(PIC_WIDTH * size, PIC_HEIGHT * size);
    }


    public boolean setListeners(List<GoMove> moves, MouseListener mouseListener) {
        for (int index = 0; index < moves.size() - 1; index++) {
            GoMove m = moves.get(index);
            int row = m.getRow();
            int col = m.getCol();
            labels[row][col].addMouseListener(mouseListener);
        }
        return false;
    }

    public boolean removeListeners(List<GoMove> moves, MouseListener mouseListener) {
        for (GoMove m : moves) {
            int row = m.getRow();
            int col = m.getCol();
            if (row != -1)
                labels[row][col].removeMouseListener(mouseListener);
        }
        return false;
    }

    public void setTool(int row, int col, Tool tool) {
        labels[row][col].setTool(tool);
    }

    public void reset(GoBoard goBoard) {
        init(goBoard);
    }
}
