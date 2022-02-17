package it.units.italiandraughts.event;

import it.units.italiandraughts.logic.Player;

public class GameOverEvent extends GameEvent {

    public GameOverEvent(Object source) {
        super(source, EventType.GAME_OVER);
    }

}