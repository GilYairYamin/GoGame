package GenericTree;

public enum Player {
    PLAYER_1, PLAYER_2;

    public Player getEnemy() {
        return switch (this) {
            default -> Player.PLAYER_2;
            case PLAYER_2 -> Player.PLAYER_1;
        };
    }
}
