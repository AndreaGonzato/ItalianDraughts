package it.units.italiandraughts.exception;

public class IllegalMovePieceException extends RuntimeException{
    public IllegalMovePieceException() {
        super("The required move is illegal");
    }

    public IllegalMovePieceException(String message) {
        super(message);
    }

}
