package it.units.italiandraughts.event;

import it.units.italiandraughts.logic.Player;

public class GameOverEvent extends GameEvent {

    private final Player winner;

    public GameOverEvent(Object source, Player winner) {
        super(source, EventType.GAME_OVER);
        this.winner = winner;
    }

    @Override
    public Player getPayload() {
        return winner;
    }
}