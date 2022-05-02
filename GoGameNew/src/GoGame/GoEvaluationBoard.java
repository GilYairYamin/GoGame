package GoGame;

public class GoEvaluationBoard {

    private GoBoard goBoard;
    private int[][] evalBoard;
    private int[][] evalTemplate;
    private GoMove lastMove;

    public GoEvaluationBoard(GoBoard board, GoMove lastMove, Tool tool){
        this.goBoard = board;
        this.lastMove = lastMove;
        init();
    }

    private void init(){
        int size = goBoard.getSize();
        this.evalTemplate = new int[size][size];
        this.evalBoard = new int[size][size];
    }

    public int getEval(){
        int sum = 0;
        for(int i = 0; i < evalBoard.length; i++){
            for(int j = 0; j < evalBoard.length; j++){
                sum += evalBoard[i][j];
            }
        }
        return sum;
    }

    public void addPiece(GoMove move){
        
    }
}