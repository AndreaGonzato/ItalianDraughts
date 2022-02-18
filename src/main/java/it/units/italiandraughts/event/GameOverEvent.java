package it.units.italiandraughts.event;

public class GameOverEvent extends GameEvent {

    public GameOverEvent(Object source) {
        super(source, EventType.GAME_OVER);
    }

}