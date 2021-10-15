package it.units.italiandraughts.event;

import it.units.italiandraughts.event.EventType;
import it.units.italiandraughts.event.GameEvent;
import it.units.italiandraughts.logic.Player;

public class SwitchActivePlayerEvent extends GameEvent {

    private final Player newActivePlayer;
    private final Player oldActivePlayer;

    public SwitchActivePlayerEvent(Object source, Player newActivePlayer, Player oldActivePlayer) {
        super(source, EventType.SWITCH_ACTIVE_PLAYER);
        this.newActivePlayer = newActivePlayer;
        this.oldActivePlayer = oldActivePlayer;
    }

    @Override
    public Player[] getPayload() {
        return new Player[] {newActivePlayer, oldActivePlayer};
    }
}
