package GoGame;

import GenericTree.GameMove;

import java.awt.*;

public class GoMove implements GameMove {
    public static final int PASS = -1;
    public static final int UNDO = -2;

    private int row;
    private int col;
    private Tool player;

    public GoMove(Tool player){
        this(PASS, PASS, player);
    }

    public GoMove(int row, int col, Tool player) {
        this.row = row;
        this.col = col;
        this.player = player;
    }

    public GoMove(GoMove move) {
        this.row = move.row;
        this.col = move.col;
        this.player = move.player;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public Tool getTool() {
        return player;
    }

    public Point getPoint() {
        return new Point(col, row);
    }

    @Override
    public String toString() {
        if(this.isPass())
            return "Pass";
        return this.row + " , " + this.col + " , " + this.player;
    }

    public boolean isPass() {
        return this.row == PASS;
    }

    public boolean isUndo(){
        return this.row == UNDO;
    }

    public void setUndo() {
        this.row = UNDO;
    }

    public void setPass() {
        this.row = PASS;
    }
}
