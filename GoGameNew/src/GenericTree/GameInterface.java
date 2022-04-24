package GenericTree;

import java.util.List;

public interface GameInterface {

    List<GameMove> getPossibleMoves(Player p);

    boolean makeMove(GameMove m);

    GameMove undoMove();

    int evaluate(Player p);
}
