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
    private final List<BlackTile[]> log;
    private final MediaPlayer mediaPlayer;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.activePlayer = player1;
        support = new PropertyChangeSupport(this);
        newTurn();
        log = new ArrayList<>();
        mediaPlayer = initMediaPlayer();
    }

    private void newTurn() {
        setActiveTile(null);
        setStatus(Status.IDLE);
        updateMovablePieces();
        List<Graph> graphs = matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor())
                        && tile.getPiece().isMovable())
                .map(this::generateGraphForTile).collect(Collectors.toList());

        graphs.forEach(Graph::explorePossibleMoves);

        List<GraphPath<Tile, Edge>> absoluteLongestPaths = graphs.stream()
                .flatMap(graph -> graph.getLongestPaths().stream())
                .collect(getLongestPaths());

        // TODO test print the cost of the absoluteLongestPaths and then the path, remove this two lines
        System.out.println(absoluteLongestPaths.get(0).getWeight());
        absoluteLongestPaths.forEach(System.out::println);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<BlackTile[]> getLog() {
        return log;
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

    public void movePiece(Piece piece, BlackTile destination){
        BlackTile source = piece.getBlackTile();
        source.removePiece();
        destination.placePiece(piece);
    }

    public void makeMove(Piece piece, BlackTile destination, boolean shouldLog) {
        new Thread(() -> {
            mediaPlayer.play();
            mediaPlayer.seek(new Duration(0));
        }).start();

        BlackTile source = piece.getBlackTile();
        movePiece(piece, destination);

        if (shouldLog){
            log.add(new BlackTile[] { source, destination });
        }
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

    private void updateMovablePieces() {
        matrixToStream(board.getTiles())
                .filter(tile -> !tile.isEmpty())
                .map(BlackTile::asBlackTile)
                .filter(tile -> tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor()))
                .forEach(this::checkNeighborsAndSetMovable);
    }

    private void checkNeighborsAndSetMovable(BlackTile blackTile) {
        Piece piece = blackTile.getPiece();
        boolean movable = piece.getReachableNeighborsBlackTiles()
                .anyMatch(tile -> tile.isEmpty() || piece.canEatNeighbor(tile.getPiece()));
        piece.setMovable(movable);
    }

    public void reset() {
        board = new Board();
        log.clear();
        activePlayer = player1;
        newTurn();
        support.removePropertyChangeListener(drawer);
        drawer = drawer.reset();
        addPropertyChangeListener(drawer);
    }

    public BlackTile getActiveTile() {
        return activeTile;
    }

    public void setActiveTile(BlackTile tile) {
        this.activeTile = tile;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }

    public Status getStatus() {
        return status;
    }

    public Board getBoard() {
        return board;
    }

    public void undo() {
        if (log.size() - 1 < 0) {
            throw new IllegalButtonClickException("An illegal click was performed on the undo button");
        }
        BlackTile[] tiles = log.remove(log.size() - 1);
        makeMove(tiles[1].getPiece(), tiles[0], false);
        drawer.updateBoard(board.getTiles());
        status = Status.IDLE;
    }

    public Graph generateGraphForTile(BlackTile source) {
        Graph graph = new Graph(board, source);
        Piece piece = source.getPiece();
        // Add edges for trivial moves (moves on empty squares, which weight 1)
        piece.getReachableNeighborsBlackTiles()
                .filter(Tile::isEmpty)
                .forEach(tile -> graph.addEdge(source, tile, 1));
        // Add edges for eating pieces
        piece.getReachableNeighborsBlackTiles()
                .filter(tile -> !tile.isEmpty() && piece.canEatNeighbor(tile.getPiece()))
                .forEach(tile -> graph.recursivelyAddEatingEdges(piece, tile.getPiece(), 1));
        return graph;
    }
}
