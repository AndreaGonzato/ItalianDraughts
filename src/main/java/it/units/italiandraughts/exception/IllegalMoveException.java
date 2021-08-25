package it.units.italiandraughts.exception;

public class IllegalMoveException extends RuntimeException{
    public IllegalMoveException() {
        super("The required move is illegal");
    }

    public IllegalMoveException(String message) {
        super(message);
    }

}
