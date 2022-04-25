package Players;

import GenericTree.GenericGameTree;
import GoGame.GoBoard;
import GoGame.GoMove;
import GoGame.Tool;
import goVisual.VisualGame;

public class ComputerPlayer extends GoPlayer {

    private static final int DEFAULT_DEPTH = 3;
    private int depth;

    public ComputerPlayer(GoBoard game, VisualGame visualGame, Tool tool) {
        super(game, visualGame, tool);
        this.depth = DEFAULT_DEPTH;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public int getDepth(){
        return this.depth;
    }

    @Override
    public GoMove getWantedMove() {
        GoBoard newBoard = new GoBoard(this.board);
        GenericGameTree t = new GenericGameTree(newBoard, GoBoard.toolToPlayer(this.tool), depth);
        //System.out.println(t.printEval());
        return (GoMove) t.getBestMove();
    }
}