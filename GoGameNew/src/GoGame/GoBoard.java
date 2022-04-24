package GoGame;

import GenericTree.GameInterface;
import GenericTree.GameMove;
import GenericTree.Player;

import java.util.*;

public class GoBoard implements GameInterface {

    private HashMap<Tool, Integer> captured;

    private Tool[][] board;
    private Stack<GoUndoMove> undoStack;
    private static final int[][] POSSIBLE_DIR = {
            { 1, 0 },
            { -1, 0 },
            { 0, 1 },
            { 0, -1 }
    };

    private void init() {
        for (GoGame.Tool[] row : board) {
            Arrays.fill(row, GoGame.Tool.EMPTY);
        }
        this.captured = new HashMap<>();
        this.captured.put(Tool.BLACK, 0);
        this.captured.put(Tool.WHITE, 0);
        this.undoStack = new Stack<>();
    }

    public GoBoard(int size) {
        board = new GoGame.Tool[size][size];
        init();
    }

    public int getBlackCaptured() {
        return getCaptured(Tool.BLACK);
    }

    public int getWhiteCaptured() {
        return getCaptured(Tool.WHITE);
    }

    public int getCaptured(Tool t) {
        return captured.get(t);
    }

    public void copyBoardState(GoBoard other) {
        if (this.board.length != other.board.length)
            return;
        for (int i = 0; i < this.board.length; i++)
            for (int j = 0; j < this.board.length; j++)
                this.board[i][j] = other.board[i][j];
        this.captured.put(Tool.BLACK, other.captured.get(Tool.BLACK));
        this.captured.put(Tool.WHITE, other.captured.get(Tool.WHITE));
    }

    public int getSize() {
        return this.board.length;
    }

    public List<GoMove> getPossibleMoves(GoGame.Tool p) {
        List<GoMove> res = new LinkedList<>();
        GoMove temp;
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++) {
                temp = new GoMove(i, j, p);
                if (isMoveLegal(temp))
                    res.add(temp);
            }
        // add Pass.
        res.add(new GoMove(p));
        return res;
    }

    public GoGame.Tool getTool(int row, int col) {
        if (isInBounds(row, col))
            return board[row][col];
        return null;
    }

    public boolean isMoveLegal(GoMove move) {
        if (move == null)
            return false;
        int row = move.getRow();
        int col = move.getCol();

        if (!isInBounds(row, col)) {
            return move.isPass();
        }

        if (board[row][col] != GoGame.Tool.EMPTY)
            return false;

        board[row][col] = move.getTool();
        boolean res = isGroupAlive(row, col);
        res = res || didCapture(row, col);
        board[row][col] = GoGame.Tool.EMPTY;
        return res;
    }

    public boolean didCapture(int row, int col) {
        int tempRow, tempCol;
        for (int[] dir : POSSIBLE_DIR) {
            tempRow = row + dir[0];
            tempCol = col + dir[1];
            if (isInBounds(tempRow, tempCol)) {
                if (board[row][col].getEnemy() == board[tempRow][tempCol])
                    if (!isGroupAlive(tempRow, tempCol))
                        return true;
            }
        }
        return false;
    }

    public boolean isGroupAlive(int row, int col) {
        int tempRow, tempCol;
        for (int[] dir : POSSIBLE_DIR) {
            tempRow = row + dir[0];
            tempCol = col + dir[1];
            if (isInBounds(tempRow, tempCol) && board[tempRow][tempCol] == Tool.EMPTY)
                return true;
        }

        boolean isGroupAlive = false;
        Tool temp = board[row][col];

        board[row][col] = Tool.MARK;
        for (int[] dir : POSSIBLE_DIR) {
            tempRow = row + dir[0];
            tempCol = col + dir[1];
            if (isInBounds(tempRow, tempCol) && board[tempRow][tempCol] == temp)
                isGroupAlive = isGroupAlive(tempRow, tempCol);
            if (isGroupAlive)
                break;
        }
        board[row][col] = temp;
        return isGroupAlive;
    }

    private boolean isInBounds(int row, int col) {
        return row >= 0 && row < board.length && col >= 0 && col < board[row].length;
    }

    @Override
    public List<GameMove> getPossibleMoves(Player p) {
        Tool t = playerToTool(p);
        return convert(getPossibleMoves(t));
    }

    public static List<GameMove> convert(List<GoMove> l) {
        List<GameMove> res = new LinkedList<>();
        for (GoMove m : l) {
            res.add(m);
        }
        return res;
    }

    @Override
    public boolean makeMove(GameMove m) {
        if (!(m instanceof GoMove))
            return false;
        GoMove move = (GoMove) m;
        return this.commitMove(move);
    }

    private boolean commitMove(GoMove move) {
        if (!isMoveLegal(move))
            return false;
        GoUndoMove undo;
        if (move.isPass()) {
            undo = new GoUndoMove(move);
            undoStack.push(undo);
            return true;
        }

        undo = new GoUndoMove(move);
        List<int[]> removed;
        int row = move.getRow(), col = move.getCol();
        int tempRow, tempCol;

        Tool current = move.getTool();
        Tool enemy = current.getEnemy();
        board[row][col] = current;

        for (int[] dir : POSSIBLE_DIR) {
            tempRow = row + dir[0];
            tempCol = col + dir[1];
            if (isInBounds(tempRow, tempCol)) {
                if (enemy == board[tempRow][tempCol])
                    if (!isGroupAlive(tempRow, tempCol)) {
                        removed = new LinkedList<>();
                        this.removeGroup(tempRow, tempCol, removed);
                        undo.addAll(removed);
                        this.captured.put(enemy, this.captured.get(enemy) + removed.size());
                    }
            }
        }
        undoStack.push(undo);
        return true;
    }

    private void removeGroup(int row, int col, List<int[]> removed) {
        int[] indexes = { row, col };
        removed.add(indexes);
        Tool temp = board[row][col];

        board[row][col] = Tool.MARK;
        int tempRow, tempCol;
        for (int[] dir : POSSIBLE_DIR) {
            tempRow = row + dir[0];
            tempCol = col + dir[1];
            if (isInBounds(tempRow, tempCol) && board[tempRow][tempCol] == temp)
                removeGroup(tempRow, tempCol, removed);
        }

        board[row][col] = Tool.EMPTY;
    }

    @Override
    public GameMove undoMove() {
        if (undoStack.isEmpty())
            return null;
        GoUndoMove undo = undoStack.pop();
        if (undo.isPass()) {
            return new GoMove(undo.getTool());
        }

        Tool player = undo.getTool();
        Tool enemy = player.getEnemy();
        int row = undo.getRow();
        int col = undo.getCol();
        board[row][col] = Tool.EMPTY;

        List<int[]> undoRemove = undo.getRemovedSpots();
        for (int[] spots : undoRemove) {
            board[spots[0]][spots[1]] = enemy;
        }
        this.captured.put(enemy, captured.get(enemy) - undoRemove.size());
        return new GoMove(row, col, player);
    }

    @Override
    public int evaluate(Player p) {
        Tool t = playerToTool(p);
        Tool enemy = t.getEnemy();
        int eval = captured.get(t) - captured.get(enemy);
        if(eval != 0)
            System.out.println(eval);
        return eval;
    }

    public static Player toolToPlayer(GoGame.Tool t) {
        return switch (t) {
            default -> null;
            case BLACK -> Player.PLAYER_1;
            case WHITE -> Player.PLAYER_2;
        };
    }

    public static GoGame.Tool playerToTool(Player p) {
        return p == Player.PLAYER_1 ? GoGame.Tool.BLACK : GoGame.Tool.WHITE;
    }

    public String toString() {
        String res = "";
        for (int i = 0; i < getSize(); i++) {
            for (int j = 0; j < getSize(); j++)
                res += switch (board[i][j]) {
                    case BLACK -> "1 ";
                    case WHITE -> "2 ";
                    default -> "0 ";
                };
            res += "\n";
        }
        return res;
    }
}
