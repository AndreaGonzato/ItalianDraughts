package it.units.italiandraughts.logic;

public interface GameEventSource {
    void addListeners(EventType eventType, GameEventListener... listener);
    void notifyListeners(GameEvent event);
}
