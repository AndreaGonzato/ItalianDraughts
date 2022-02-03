package it.units.italiandraughts.logic;

import it.units.italiandraughts.event.*;
import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.tile.BlackTile;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.jgrapht.GraphPath;

import java.io.File;
import java.net.URL;
import java.util.*;

import static it.units.italiandraughts.logic.StaticUtil.*;

public class Game implements GameEventSource {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private BlackTile activeTile;
    private final List<Move> moves;
    private List<GraphPath<BlackTile, Edge>> absoluteLongestPaths;
    private final HashMap<EventType, List<GameEventListener>> listenersMap;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.activePlayer = player1;
        listenersMap = new HashMap<>();
        moves = new ArrayList<>();
        updateMovablePiecesOfPlayer(activePlayer);
        updateAbsoluteLongestPath();
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
        updateMovablePiecesOfPlayer(inactivePlayer);
        if (countMovablePiecesOfPlayer(inactivePlayer) == 0) {
            notifyListeners(new GameOverEvent(this, activePlayer));
        }
        toggleActivePlayer();
        updateAbsoluteLongestPath();
    }

    private void updateAbsoluteLongestPath() {
        absoluteLongestPaths = matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor())
                        && tile.getPiece().isMovable())
                .map(tile -> new Graph(tile, this))
                .flatMap(graph -> graph.getLongestPaths().stream())
                .collect(Graph.getLongestPathsCollector());
    }

    private int countMovablePiecesOfPlayer(Player player){
        return (int) matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(player.getPieceColor())
                        && tile.getPiece().isMovable())
                .count();
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

    public Move moveAndLog(Piece piece, List<BlackTile> steps) {
        Move move = new Move(piece, piece.getBlackTile(), steps.get(steps.size() - 1), steps);
        move.make();
        moves.add(move);
        return move;
    }

    public void makeMove(Piece piece, List<BlackTile> steps) {
        playSound();
        moveAndLog(piece, steps);
        newTurn();
    }

    public void undoLastMove() {
        if (moves.size() - 1 < 0) {
            throw new IllegalButtonClickException("An illegal click was performed on the undo button");
        }
        Move move = moves.remove(moves.size() - 1);
        move.undo();
    }

    private MediaPlayer initMediaPlayer() {
        String path = "sounds" + File.separatorChar + "movePiece.wav";
        URL resource = Objects.requireNonNull(getClass().getResource(path));
        Media media = new Media(resource.toString());
        return new MediaPlayer(media);
    }

    private void updateMovablePiecesOfPlayer(Player player) {
        matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(player.getPieceColor()))
                .forEach(tile -> tile.getPiece().updateMovable());
    }

    public void undo() {
        undoLastMove();
        newTurn();
    }

    public BlackTile getActiveTile() {
        return activeTile;
    }

    public void setActiveTile(BlackTile tile) {
        this.activeTile = tile;
    }

    public Board getBoard() {
        return board;
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
