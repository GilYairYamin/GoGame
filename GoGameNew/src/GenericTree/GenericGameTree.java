package GenericTree;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class GenericGameTree {

    public static final int MIN_POSSIBLE = Integer.MIN_VALUE + 10;
    public static final int MAX_POSSIBLE = Integer.MAX_VALUE - 10;
    private GameInterface game;
    private Player currentTurn;
    private List<GameMove> possibleMoves;
    private int[] eval;

    public GenericGameTree(GameInterface game, Player currentTurn, int depth) {
        this.game = game;
        this.currentTurn = currentTurn;
        init(depth);
    }

    private void init(int depth) {
        this.possibleMoves = game.getPossibleMoves(currentTurn);
        shuffleList(this.possibleMoves);

        this.eval = new int[this.possibleMoves.size()];
        int i = 0;
        for (GameMove move : possibleMoves) {
            eval[i++] = -negamax(move, depth, MIN_POSSIBLE, MAX_POSSIBLE, currentTurn);
        }
    }

    public GameMove getBestMove() {
        int max = 0;
        for (int i = 1; i < eval.length; i++)
            if (eval[max] < eval[i])
                max = i;

        return possibleMoves.get(max);
    }

    private int negamax(GameMove currentMove, int depth, int alpha, int beta, Player p) {
        game.makeMove(currentMove);
        int value = MIN_POSSIBLE;
        if (depth <= 0){
            value = game.evaluate(p);
        }
        else {
            Player enemy = p.getEnemy();
            List<GameMove> childMoves = game.getPossibleMoves(enemy);
            for (GameMove childMove : childMoves) {
                value = Math.max(value, -negamax(childMove, depth - 1, -beta, -alpha, enemy));
                alpha = Math.max(value, alpha);
                if (alpha >= beta)
                    break;
            }
        }
        game.undoMove();
        return value;
    }

    private void shuffleList(List<GameMove> list) {
        Random rnd = new Random();
        List<GameMove> temp = new LinkedList<>();
        while (!list.isEmpty()) {
            temp.add(list.remove(rnd.nextInt(list.size())));
        }
        while (!temp.isEmpty())
            list.add(temp.remove(rnd.nextInt(temp.size())));
    }

    public String printEval(){
        return Arrays.toString(eval);
    }
}
