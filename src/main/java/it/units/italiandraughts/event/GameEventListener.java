package it.units.italiandraughts.event;

import java.util.EventListener;

public interface GameEventListener extends EventListener {
    void onGameEvent(GameEvent event);
}
