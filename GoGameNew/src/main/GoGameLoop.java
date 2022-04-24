package main;

import GoGame.GoBoard;
import GoGame.GoMove;
import GoGame.Tool;
import Players.ComputerPlayer;
import Players.GoPlayer;
import Players.HumanPlayer;
import goVisual.VisualGame;

import javax.swing.*;

public class GoGameLoop implements Runnable {

    public static final int REST_TIME = 100;

    public static final int HUMAN = -1;
    public static final int MAX_DEPTH = 5;

    private boolean running;
    private GoBoard board;
    private VisualGame vBoard;
    private GoPlayer black;
    private GoPlayer white;

    private boolean blackTurn;

    public GoGameLoop(int size, int blackDepth, int whiteDepth) {
        init(size, blackDepth, whiteDepth);
    }

    private GoPlayer createPlayer(Tool tool, int depth) {
        if (depth == -1)
            return new HumanPlayer(this.board, this.vBoard, tool);
        ComputerPlayer cp = new ComputerPlayer(this.board, this.vBoard, tool);
        cp.setDepth(Math.min(MAX_DEPTH, depth));
        return cp;
    }

    @Override
    public void run() {
        GoMove move;
        int passCounter = 0;
        this.running = true;

        while (running) {
            if (blackTurn) {
                move = black.getWantedMove();
                this.board.makeMove(move);
            } else {
                move = white.getWantedMove();
                this.board.makeMove(move);
            }

            this.vBoard.updateGame();
            passCounter = move.isPass() ? passCounter + 1 : 0;
            if (passCounter >= 2) {
                running = false;
                JOptionPane.showMessageDialog(null, "Both players passed.");
            }
            blackTurn = !blackTurn;
            try {
                Thread.sleep(REST_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public GoBoard getGoBoard() {
        return board;
    }

    public VisualGame getVisualBoard() {
        return vBoard;
    }

    public void stopRunning() {
        if (!running)
            return;
        this.running = false;
        try {
            Thread.sleep(REST_TIME * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getBlackDepth() {
        if (black instanceof ComputerPlayer) {
            return ((ComputerPlayer) black).getDepth();
        }
        return -1;
    }

    public int getWhiteDepth() {
        if (white instanceof ComputerPlayer) {
            return ((ComputerPlayer) white).getDepth();
        }
        return -1;
    }

    public void resetGame() {
        this.resetGame(board.getSize());
    }

    public void resetGame(int size) {
        this.stopRunning();
        this.board = new GoBoard(size);
        this.vBoard = new VisualGame(board);
    }

    private void init(int size, int blackDepth, int whiteDepth) {
        this.running = false;
        this.board = new GoBoard(size);
        this.vBoard = new VisualGame(board);

        this.black = createPlayer(Tool.BLACK, blackDepth);
        this.white = createPlayer(Tool.WHITE, whiteDepth);

        this.blackTurn = true;
    }

    public void setBlackDepth(int depth) {
        this.black = createPlayer(Tool.BLACK, depth);
    }

    public void setWhiteDepth(int depth) {
        this.white = createPlayer(Tool.WHITE, depth);
    }

    public int getCurrentSize() {
        return this.board.getSize();
    }
}
