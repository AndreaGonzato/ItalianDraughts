package it.units.italiandraughts.logic;

import it.units.italiandraughts.event.*;
import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.exception.InvalidPlayersException;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

public class Game implements GameEventSource {
    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private BlackTile activeTile;
    private final List<Move> moves;
    private List<GraphPath<BlackTile, DefaultWeightedEdge>> absoluteLongestPaths;
    private final HashMap<EventType, List<GameEventListener>> listenersMap;
    private Player winnerPlayer;

    public Game(Player player1, Player player2) {
        if (player1.getPieceColor().equals(player2.getPieceColor())) {
            throw new InvalidPlayersException();
        }

        this.player1 = player1;
        this.player2 = player2;

        if (player1.getPieceColor().equals(PieceColor.WHITE)) {
            this.activePlayer = player1;
        } else {
            this.activePlayer = player2;
        }

        listenersMap = new HashMap<>();
        moves = new ArrayList<>();
        activePlayer.updateMovablePieces();
        updateAbsoluteLongestPaths();
    }

    @Override
    public void addListeners(EventType eventType, GameEventListener... listeners) {
        if (!listenersMap.containsKey(eventType)) {
            listenersMap.put(eventType, new LinkedList<>());
        }
        listenersMap.get(eventType).addAll(List.of(listeners));
    }

    @Override
    public void notifyListeners(GameEvent event) {
        listenersMap.get(event.getEventType()).forEach(listener -> listener.onGameEvent(event));
    }

    private void newTurn() {
        Player inactivePlayer = activePlayer.equals(player1) ? player2 : player1;
        inactivePlayer.updateMovablePieces();
        if (inactivePlayer.countMovablePieces() == 0) {
            winnerPlayer = activePlayer;
            notifyListeners(new GameOverEvent(this));
            return;
        }
        toggleActivePlayer();
        updateAbsoluteLongestPaths();
    }

    private void updateAbsoluteLongestPaths() {
        absoluteLongestPaths = activePlayer.getPieces()
                .stream().map(Piece::getBlackTile)
                .map(DijkstraGraph::new)
                .flatMap(graph -> graph.calculateLongestPaths().stream())
                .collect(DijkstraGraph.getLongestPathsCollector());
    }

    private void toggleActivePlayer() {
        if (player1.equals(activePlayer)) {
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
        notifyListeners(new SwitchActivePlayerEvent(this));
    }

    private GraphPath<BlackTile, DefaultWeightedEdge> getLongestPathFromActiveTileToDestination(BlackTile destination) {
        return absoluteLongestPaths.stream()
                .filter(path -> path.getEndVertex().equals(destination)
                        && path.getStartVertex().equals(activeTile))
                .findAny()
                .orElseThrow(() -> new IllegalMoveException(
                        "The destination must be the final BlackTile of one of the absolute longest paths."
                ));
    }

    Move makeAndSaveMove(Piece piece, List<BlackTile> steps) {
        Move move = new Move(piece, steps);
        move.make();
        moves.add(move);
        return move;
    }

    public void moveActivePieceTo(BlackTile destination) {
        Piece piece = activeTile.getPiece();
        GraphPath<BlackTile, DefaultWeightedEdge> longestPathToDestination;
        longestPathToDestination = getLongestPathFromActiveTileToDestination(destination);
        makeAndSaveMove(piece, longestPathToDestination.getVertexList());
        newTurn();
    }

    void undoLastMove() {
        if (moves.size() - 1 < 0) {
            throw new IllegalButtonClickException("An illegal click was performed on the undo button.");
        }
        Move move = moves.remove(moves.size() - 1);
        move.undo();
    }

    public void undo() {
        undoLastMove();
        newTurn();
    }

    public void setActiveTile(BlackTile tile) {
        this.activeTile = tile;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public List<GraphPath<BlackTile, DefaultWeightedEdge>> getAbsoluteLongestPaths() {
        return absoluteLongestPaths;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public Player getInactivePlayer() {
        return activePlayer.equals(player1) ? player2 : player1;
    }

    public Player getWinnerPlayer() {
        return winnerPlayer;
    }

    public List<Move> getMoves() {
        return moves;
    }

}
