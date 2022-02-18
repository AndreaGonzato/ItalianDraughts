package it.units.italiandraughts.event;

public class SwitchActivePlayerEvent extends GameEvent {

    public SwitchActivePlayerEvent(Object source) {
        super(source, EventType.SWITCH_ACTIVE_PLAYER);
    }

}
