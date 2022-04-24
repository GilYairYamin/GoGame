package GoGame;

import java.util.LinkedList;
import java.util.List;

public class GoUndoMove extends GoMove {

    private List<int[]> removedSpots;

    public GoUndoMove(GoMove move) {
        super(move);
        removedSpots = new LinkedList<>();
    }

    public void addAll(List<int[]> removeGroup) {
        removedSpots.addAll(removeGroup);
    }

    public List<int[]> getRemovedSpots() {
        return removedSpots;
    }
}
