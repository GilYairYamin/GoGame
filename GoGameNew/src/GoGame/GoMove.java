package GoGame;

import GenericTree.GameMove;

import java.awt.*;

public class GoMove implements GameMove {

    private final int row;
    private final int col;
    private final Tool player;

    public GoMove(Tool player){
        this(-1, -1, player);
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
        return this.row == -1 && this.col == -1;
    }
}
