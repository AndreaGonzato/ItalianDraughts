package it.units.italiandraughts.logic;

public class GameOverEvent extends GameEvent {

    private final Player winner;

    GameOverEvent(Object source, Player winner) {
        super(source, EventType.GAME_OVER);
        this.winner = winner;
    }

    @Override
    public Player getPayload() {
        return winner;
    }
}