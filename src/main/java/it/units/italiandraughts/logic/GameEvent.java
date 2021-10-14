package it.units.italiandraughts.logic;

import java.util.EventObject;

public abstract class GameEvent extends EventObject {

    private final EventType eventType;

    GameEvent(Object source, EventType eventType) {
        super(source);
        this.eventType = eventType;
    }

    public abstract Object getPayload();

    public EventType getEventType() {
        return this.eventType;
    }

}
