package it.units.italiandraughts.logic;

import it.units.italiandraughts.event.*;
import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.ui.BoardDrawer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.jgrapht.GraphPath;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static it.units.italiandraughts.logic.StaticUtil.*;

public class Game implements GameEventSource {
    private final Board board;
    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private BlackTile activeTile;
    private BoardDrawer boardDrawer;
    private final List<Move> moves;
    private final MediaPlayer mediaPlayer;
    private List<GraphPath<BlackTile, Edge>> absoluteLongestPaths;
    private final HashMap<EventType, List<GameEventListener>> listenersMap;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.activePlayer = player1;
        newTurn();
        moves = new ArrayList<>();
        mediaPlayer = initMediaPlayer();
        listenersMap = new HashMap<>();
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
        updateMovablePiecesOfActivePlayer();
        if (countMovablePiecesOfActivePlayer() == 0) {
            notifyListeners(new GameOverEvent(this, activePlayer.equals(player1) ? player2 : player1));
        }
        List<Graph> graphs = matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor())
                        && tile.getPiece().isMovable())
                .map(tile -> new Graph(tile, this)).collect(Collectors.toList());

        graphs.forEach(Graph::explorePossibleMoves);

        absoluteLongestPaths = graphs.stream()
                .flatMap(graph -> graph.getLongestPaths().stream())
                .collect(Graph.getLongestPathsCollector());

    }

    private int countMovablePiecesOfActivePlayer(){
        return (int) matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor())
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
        finalizeMove();
    }

    public void undoLastMove() {
        if (moves.size() - 1 < 0) {
            throw new IllegalButtonClickException("An illegal click was performed on the undo button");
        }
        Move move = moves.remove(moves.size() - 1);
        move.undo();
    }

    private void finalizeMove() {
        boardDrawer.updateBoard(board.getTiles());
        toggleActivePlayer();
        newTurn();
    }

    private MediaPlayer initMediaPlayer() {
        String path = "sounds" + File.separatorChar + "movePiece.mp3";
        URL resource = Objects.requireNonNull(getClass().getResource(path));
        Media media = new Media(resource.toString());
        return new MediaPlayer(media);
    }

    private void updateMovablePiecesOfActivePlayer() {
        matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor()))
                .forEach(tile -> tile.getPiece().updateMovable());
    }

    public void undo() {
        undoLastMove();
        finalizeMove();
        boardDrawer.clearHighlightingAndCircles();
    }

    public BlackTile getActiveTile() {
        return activeTile;
    }

    public void setActiveTile(BlackTile tile) {
        this.activeTile = tile;
    }

    public void setDrawer(BoardDrawer boardDrawer) {
        this.boardDrawer = boardDrawer;
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
