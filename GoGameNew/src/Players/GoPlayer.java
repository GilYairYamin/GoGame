package Players;

import GoGame.GoBoard;
import GoGame.GoMove;
import GoGame.Tool;
import goVisual.VisualGame;

public abstract class GoPlayer {

    protected GoBoard board;
    protected VisualGame visualGame;
    protected Tool tool;

    public GoPlayer(GoBoard game, VisualGame visualGame, Tool tool) {
        this.board = game;
        this.visualGame = visualGame;
        this.tool = tool;
    }

    public abstract GoMove getWantedMove();
}
