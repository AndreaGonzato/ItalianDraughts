package it.units.italiandraughts.logic;

public class SwitchActivePlayerEvent extends GameEvent {

    private final Player newActivePlayer;
    private final Player oldActivePlayer;

    SwitchActivePlayerEvent(Object source, Player newActivePlayer, Player oldActivePlayer) {
        super(source, EventType.SWITCH_ACTIVE_PLAYER);
        this.newActivePlayer = newActivePlayer;
        this.oldActivePlayer = oldActivePlayer;
    }

    @Override
    public Player[] getPayload() {
        return new Player[] {newActivePlayer, oldActivePlayer};
    }
}
