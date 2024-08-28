package boardgame;

public record Position(int row, int col) {
    /**
     * Returns a string representation of the Position object.
     *
     * @return a string representation of the Position object
     */
    public String toString() {
        return String.format("(%d,%d)", row, col);
    }

}

