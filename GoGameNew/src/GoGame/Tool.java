package GoGame;

public enum Tool {
    EMPTY, BLACK, WHITE, MARK;

    public Tool getEnemy() {
        return switch (this) {
            default -> this;
            case BLACK -> WHITE;
            case WHITE -> BLACK;
        };
    }
}
