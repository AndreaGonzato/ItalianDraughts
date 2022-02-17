package it.units.italiandraughts.exception;

public class InvalidPlayersException extends ItalianDraughtsException{

    public InvalidPlayersException() {
        super("Two players can not have the same PieceColor in a Game.");
    }

}
