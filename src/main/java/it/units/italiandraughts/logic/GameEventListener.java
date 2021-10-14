package it.units.italiandraughts.logic;

import java.util.EventListener;

public interface GameEventListener extends EventListener {
    void onGameEvent(GameEvent event);
}
