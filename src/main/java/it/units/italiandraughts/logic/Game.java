package it.units.italiandraughts.logic;

import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.ui.Drawer;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import org.jgrapht.GraphPath;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

import static it.units.italiandraughts.logic.StaticUtil.*;

public class Game {
    private Board board;
    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private Status status;
    private BlackTile activeTile;
    private Drawer drawer;
    private final PropertyChangeSupport support;
    private final List<Move> moves;
    private final MediaPlayer mediaPlayer;
    private List<GraphPath<BlackTile, Edge>> absoluteLongestPaths;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.activePlayer = player1;
        support = new PropertyChangeSupport(this);
        newTurn();
        moves = new ArrayList<>();
        mediaPlayer = initMediaPlayer();
    }

    private void newTurn() {
        setActiveTile(null);
        setStatus(Status.IDLE);
        updateMovablePiecesOfActivePlayer();
        if (countMovablePiecesOfActivePlayer() == 0) {
            support.firePropertyChange("winner", null, activePlayer.equals(player1) ?
                    player2 : player1);
        }
        List<Graph> graphs = matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor())
                        && tile.getPiece().isMovable())
                .map(this::generateGraphForTile).collect(Collectors.toList());

        graphs.forEach(Graph::explorePossibleMoves);

        absoluteLongestPaths = graphs.stream()
                .flatMap(graph -> graph.getLongestPaths().stream())
                .collect(getLongestPaths());

    }

    private int countMovablePiecesOfActivePlayer(){
        return (int) matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor())
                        && tile.getPiece().isMovable())
                .count();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    private void toggleActivePlayer() {
        final Player oldActivePlayer = activePlayer;
        if (player1.equals(activePlayer)) {
            activePlayer = player2;
        } else {
            activePlayer = player1;
        }
        support.firePropertyChange("activePlayer", oldActivePlayer, activePlayer);
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
        drawer.updateBoard(board.getTiles());
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
                .forEach(this::checkNeighborsAndSetMovable);
    }

    private void checkNeighborsAndSetMovable(BlackTile blackTile) {
        Piece piece = blackTile.getPiece();
        boolean movable = piece.getReachableNeighboringBlackTiles()
                .anyMatch(tile -> tile.isEmpty() || piece.canEatNeighbor(tile.getPiece()));
        piece.setMovable(movable);
    }

    public void reset() {
        board = new Board();
        moves.clear();
        activePlayer = player1;
        newTurn();
        support.removePropertyChangeListener(drawer);
        drawer = drawer.reset();
        addPropertyChangeListener(drawer);
    }

    public void undo() {
        undoLastMove();
        finalizeMove();
        drawer.clearHighlightingAndCircles();
    }

    public Graph generateGraphForTile(BlackTile source) {
        Graph graph = new Graph(source, this);
        Piece piece = source.getPiece();
        // Add edges for trivial moves (moves on empty squares, which weight 1)
        piece.getReachableNeighboringBlackTiles()
                .filter(Tile::isEmpty)
                .forEach(tile -> graph.addEdge(source, tile, 1));
        // Add edges for eating pieces
        piece.getReachableNeighboringBlackTiles()
                .filter(tile -> !tile.isEmpty() && piece.canEatNeighbor(tile.getPiece()))
                .forEach(tile -> graph.recursivelyAddEatingEdges(piece, tile.getPiece(), 1));
        return graph;
    }

    public BlackTile getActiveTile() {
        return activeTile;
    }

    public void setActiveTile(BlackTile tile) {
        this.activeTile = tile;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
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
