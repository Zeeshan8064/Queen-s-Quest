package boardgame;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;

public class BoardGameMoveSelector {


    /**
     * The phases of the move selection process.
     */
    public enum Phase {
        SELECT_FROM,
        SELECT_TO,
        READY_TO_MOVE

    }

    private BoardGame model;
    private ReadOnlyObjectWrapper<Phase> phase = new ReadOnlyObjectWrapper<>(Phase.SELECT_FROM);
    private boolean invalidSelection = false;
    private Position from;
    private Position to;

    /**
     * Constructs a move selector with the given game model.
     *
     * @param model The Board Game model.
     */
    public BoardGameMoveSelector(BoardGame model) {
        this.model = model;
    }




    /**
     * Gets the property representing the current phase of the move selection process.
     *
     * @return The read-only property of the current phase.
     */
    public ReadOnlyObjectProperty<Phase> phaseProperty() {
        return phase.getReadOnlyProperty();
    }

    /**
     * Checks if the move selection process is in the "READY_TO_MOVE" phase.
     *
     * @return {@code true} if ready to move, {@code false} otherwise.
     */
    public boolean isReadyToMove() {
        return phase.get() == Phase.READY_TO_MOVE;
    }

    /**
     * Selects a position based on the current phase of the move selection process.
     *
     * @param position The position to select.
     */
    public void select(Position position) {
        switch (phase.get()) {
            case SELECT_FROM -> selectFrom(position);
            case SELECT_TO -> selectTo(position);
            case READY_TO_MOVE -> throw new IllegalStateException();
        }
    }

    /**
     * Handles the selection of the source position.
     *
     * @param position The source position to select.
     */
    private void selectFrom(Position position) {
        if (!model.isEmpty(position)) {
            from = position;
            phase.set(Phase.SELECT_TO);
            invalidSelection = false;
        } else {
            invalidSelection = true;
        }
    }


    /**
     * Handles the selection of the target position.
     *
     * @param position The target position to select.
     */
    private void selectTo(Position position) {
        if (model.canMove(from, position)) {
            to = position;
            phase.set(Phase.READY_TO_MOVE);
            invalidSelection = false;
        } else {
            invalidSelection = true;
        }
    }

    /**
     * Gets the selected source position.
     *
     * @return The selected source position.
     * @throws IllegalStateException if called when not in the "SELECT_FROM" phase.
     */
    public Position getFrom() {
        if (phase.get() == Phase.SELECT_FROM) {
            throw new IllegalStateException();
        }
        return from;
    }


    /**
     * Gets the selected target position.
     *
     * @return The selected target position.
     * @throws IllegalStateException if called when not in the "READY_TO_MOVE" phase.
     */
    public Position getTo() {
        if (phase.get() != Phase.READY_TO_MOVE) {
            throw new IllegalStateException();
        }
        return to;
    }

    /**
     * Checks if the current selection is invalid.
     *
     * @return {@code true} if the current selection is invalid, {@code false} otherwise.
     */
    public boolean isInvalidSelection() {
        return invalidSelection;
    }

    /**
     * Executes the move based on the selected positions.
     *
     * @throws IllegalStateException if called when not in the "READY_TO_MOVE" phase.
     */
    public void makeMove() {
        if (phase.get() != Phase.READY_TO_MOVE) {
            throw new IllegalStateException();
        }
        model.makeMove(from, to);
        reset();
    }

    /**
     * Resets the move selector to its initial state.
     * Clears the selected positions and sets the phase to "SELECT_FROM".
     */
    public void reset() {
        from = null;
        to = null;
        phase.set(Phase.SELECT_FROM);
        invalidSelection = false;
    }

}
