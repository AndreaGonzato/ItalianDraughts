package it.units.italiandraughts.logic;

import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.exception.IllegalMoveException;
import it.units.italiandraughts.ui.Drawer;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.*;

import java.util.stream.Collectors;

import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class Game {
    private Board board;
    private final Player player1;
    private final Player player2;
    private Player activePlayer;
    private Status status;
    private Tile activeTile;
    private Drawer drawer;
    private final PropertyChangeSupport support;
    private final List<int[]> log;

    public Game(Board board, Player player1, Player player2) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        support = new PropertyChangeSupport(this);
        newTurn();
        log = new ArrayList<>();
    }

    private void newTurn() {
        setActiveTile(null);
        setStatus(Status.IDLE);
        toggleActivePlayer();
        updateMovablePieces();
        List<Graph> graphs = Arrays.stream(board.getTiles()).flatMap(Arrays::stream)
                .filter(tile -> !tile.isEmpty() && tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor())
                                && tile.getPiece().isMovable())
                .map(this::generateGraphForTile).collect(Collectors.toList());
        graphs.forEach(Graph::explorePossibleMoves);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public List<int[]> getLog() {
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

    public void move(int fromX, int fromY, int toX, int toY, boolean shouldLog) {
        if ((toX + toY) % 2 == 1) {
            throw new IllegalMoveException("The required move is illegal because no piece can stand on a white tile");
        }
        Tile[][] tiles = getBoard().getTiles();
        Piece piece = tiles[fromY][fromX].getPiece();
        tiles[fromY][fromX].removePiece();
        tiles[toY][toX].placePiece(piece);
        if (shouldLog) {
            log.add(IntStream.of(fromX, fromY, toX, toY).toArray());
        }
        newTurn();
    }

    private void updateMovablePieces() {
        Arrays.stream(board.getTiles()).flatMap(Arrays::stream)
                .filter(tile -> !tile.isEmpty() && tile.getPiece().getPieceColor().equals(activePlayer.getPieceColor()))
                .forEach(this::checkNeighborsAndSetMovable);
    }

    private void checkNeighborsAndSetMovable(Tile tile) {
        boolean movable = getReachableNeighbors(tile).stream()
                .anyMatch(neighbor -> neighbor.isEmpty() || canEat(tile, neighbor));
        tile.getPiece().setMovable(movable);
    }

    private List<Tile> getReachableNeighbors(Tile tile) {
        List<Tile> neighbors = new ArrayList<>();
        int x = tile.getX();
        int y = tile.getY();

        BiPredicate<Integer, Integer> areValidCoordinatesOfTileBiPredicate = (coordinateTargetX, coordinateTargetY) -> (coordinateTargetX < Board.SIZE && coordinateTargetX >= 0) && (coordinateTargetY < Board.SIZE && coordinateTargetY >= 0);

        Optional<Tile> topLeftTile = Arrays.stream(board.getTiles()).flatMap(Arrays::stream).filter(tiles -> areValidCoordinatesOfTileBiPredicate.test(x-1, y-1)).findAny();
        Optional<Tile> topRightTile = Arrays.stream(board.getTiles()).flatMap(Arrays::stream).filter(tiles -> areValidCoordinatesOfTileBiPredicate.test(x+1, y-1)).findAny();
        Optional<Tile> bottomLeftTile = Arrays.stream(board.getTiles()).flatMap(Arrays::stream).filter(tiles -> areValidCoordinatesOfTileBiPredicate.test(x-1, y+1)).findAny();
        Optional<Tile> bottomRightTile = Arrays.stream(board.getTiles()).flatMap(Arrays::stream).filter(tiles -> areValidCoordinatesOfTileBiPredicate.test(x+1, y+1)).findAny();

        switch (tile.getPiece().getPieceType()) {
            case MAN -> {
                if (activePlayer.equals(player1)) {
                    topLeftTile.ifPresent(neighbors::add);
                    topRightTile.ifPresent(neighbors::add);
                } else {
                    bottomLeftTile.ifPresent(neighbors::add);
                    bottomRightTile.ifPresent(neighbors::add);
                }
            }
            case KING -> {
                topLeftTile.ifPresent(neighbors::add);
                topRightTile.ifPresent(neighbors::add);
                bottomLeftTile.ifPresent(neighbors::add);
                bottomRightTile.ifPresent(neighbors::add);
            }
        }

        return neighbors;
    }

    private boolean canEat(Tile fromTile, Tile overTile) {
        if (!overTile.isEmpty() && !overTile.getPiece().getPieceColor().equals(activePlayer.getPieceColor())) {
            int deltaX = overTile.getX() - fromTile.getX();
            int deltaY = overTile.getY() - fromTile.getY();
            int newX = overTile.getX() + deltaX;
            int newY = overTile.getY() + deltaY;
            return isValidCoordinateOfATile(newX, newY) && board.getTiles()[newY][newX].isEmpty();
        }
        return false;
    }

    private boolean isValidCoordinateOfATile(int x, int y) {
        return (x < Board.SIZE && x >= 0) && (y < Board.SIZE && y >= 0);
    }

    public void reset() {
        board = new Board();
        log.clear();
        newTurn();
        support.removePropertyChangeListener(drawer);
        drawer = drawer.reset();
        addPropertyChangeListener(drawer);
    }

    public Tile getActiveTile() {
        return activeTile;
    }

    public void setActiveTile(Tile tile) {
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
        int[] coordinates = log.remove(log.size() - 1);
        move(coordinates[2], coordinates[3], coordinates[0], coordinates[1], false);
        drawer.updateBoard(board.getTiles());
        status = Status.IDLE;
    }

    public Graph generateGraphForTile(Tile source) {
        Graph graph = new Graph(board, source);
        // For now, this only adds edges for trivial moves (moves on empty squares, which weight 1)
        getReachableNeighbors(source).stream().filter(Tile::isEmpty).forEach(tile -> graph.addEdge(source, tile, 1));
        return graph;
    }
}