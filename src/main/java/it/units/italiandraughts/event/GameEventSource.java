package it.units.italiandraughts.event;

public interface GameEventSource {
    void addListeners(EventType eventType, GameEventListener... listener);
    void notifyListeners(GameEvent event);
}
