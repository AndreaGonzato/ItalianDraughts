package it.units.italiandraughts.logic;

public class Game {

    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private Status status;
    private Tile source;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        activePlayer = player1;
        status = Status.IDLE;
    }

    public void toggleActivePlayer() {
        if (player1.equals(activePlayer)) {
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
    }

    public Tile getSource() {
        return source;
    }

    public void setSource(Tile tile) {
        this.source = tile;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public Board getBoard() {
        return board;
    }

}