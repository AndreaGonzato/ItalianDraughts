package it.units.italiandraughts.logic;

import it.units.italiandraughts.event.*;
import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.jgrapht.GraphPath;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Stream;

public class Game implements GameEventSource {
    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private BlackTile activeTile;
    private final List<Move> moves;
    private List<GraphPath<BlackTile, Edge>> absoluteLongestPaths;
    private final HashMap<EventType, List<GameEventListener>> listenersMap;

    public Game(Player player1, Player player2) {

        if (player1.getPieceColor().equals(player2.getPieceColor())){
            System.err.println("Two player can not have the same PieceColor in a Game. The player1 will be the white and player2 will be black");
            player1 = new Player(player1.getName(), PieceColor.WHITE);
            player2 = new Player(player2.getName(), PieceColor.BLACK);
        }

        this.player1 = player1;
        this.player2 = player2;

        if (player1.getPieceColor().equals(PieceColor.WHITE)){
            this.activePlayer = player1;
        }else{
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
        setActiveTile(null);
        Player inactivePlayer = activePlayer.equals(player1) ? player2 : player1;
        inactivePlayer.updateMovablePieces();
        if (inactivePlayer.countMovablePieces() == 0) {
            notifyListeners(new GameOverEvent(this, activePlayer));
        }
        toggleActivePlayer();
        updateAbsoluteLongestPaths();
    }

    private void updateAbsoluteLongestPaths() {
        absoluteLongestPaths = activePlayer.getPieces()
                .stream().map(Piece::getBlackTile)
                .map(tile -> new Graph(tile, this))
                .flatMap(graph -> graph.getLongestPaths().stream())
                .collect(Graph.getLongestPathsCollector());
    }

    private void toggleActivePlayer() {
        final Player oldActivePlayer = activePlayer;
        if (player1.equals(activePlayer)) {
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
        notifyListeners(new SwitchActivePlayerEvent(this, activePlayer, oldActivePlayer));
    }

    private void playSound() {
        new Thread(() -> {
            MediaPlayer mediaPlayer = initMediaPlayer();
            mediaPlayer.play();
            mediaPlayer.seek(new Duration(0));
        }).start();
    }

    private GraphPath<BlackTile, Edge> getLongestPathFromActiveTileToDestination(BlackTile destination) {
        return absoluteLongestPaths.stream()
                .filter(path -> path.getEndVertex().equals(destination)
                        && path.getStartVertex().equals(activeTile))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
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
        GraphPath<BlackTile, Edge> longestPathToDestination = getLongestPathFromActiveTileToDestination(destination);
        playSound();
        makeAndSaveMove(piece, longestPathToDestination.getVertexList());
        newTurn();
    }

    void undoLastMove() {
        if (moves.size() - 1 < 0) {
            throw new IllegalButtonClickException("An illegal click was performed on the undo button");
        }
        Move move = moves.remove(moves.size() - 1);
        move.undo();
    }

    private MediaPlayer initMediaPlayer() {
        String path = "sounds" + File.separatorChar + "movePiece.mp3";
        URL resource = Objects.requireNonNull(getClass().getResource(path));
        Media media = new Media(resource.toString());
        return new MediaPlayer(media);
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

    public List<GraphPath<BlackTile, Edge>> getAbsoluteLongestPaths() {
        return absoluteLongestPaths;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<Move> getMoves() {
        return moves;
    }

}
