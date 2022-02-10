package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.logic.tile.Tile;
import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class Graph {

    private static final float EATING_KING_MULTIPLIER = 1.2f;
    private final SimpleDirectedWeightedGraph<BlackTile, Edge> graph;
    private final BlackTile source;
    private final List<BlackTile> possibleDestinations;
    private final List<GraphPath<BlackTile, Edge>> longestPaths;
    private final Game game;

    Graph(BlackTile source, Game game) {
        graph = new SimpleDirectedWeightedGraph<>(Edge.class);
        this.source = source;
        this.game = game;
        possibleDestinations = new ArrayList<>();
        longestPaths = new ArrayList<>();
        addVertices();
        initializePaths();
        explorePossibleMoves();
    }

    private void addVertices(){
        matrixToStream(Board.getBoard().getTiles()).filter(tile -> tile instanceof BlackTile)
                .map(BlackTile::asBlackTile)
                .forEach(graph::addVertex);
    }

    private void initializePaths() {
        Piece piece = source.getPiece();
        piece.getReachableNeighboringBlackTiles()
                .filter(Tile::isEmpty)
                .forEach(tile -> addEdge(source, tile, 1));
        piece.getReachableNeighboringBlackTiles()
                .filter(tile -> !tile.isEmpty() && piece.canEatNeighbor(tile.getPiece()))
                .forEach(tile -> recursivelyAddEatingEdges(piece, tile.getPiece(), 1));
    }

    void addEdge(BlackTile source, BlackTile target, double weight){
        Edge e1 = graph.addEdge(source, target);
        graph.setEdgeWeight(e1, weight);
        possibleDestinations.add(target);
    }

    void recursivelyAddEatingEdges(Piece eatingPiece, Piece eatenPiece, int step) {
        BlackTile landingTile = eatingPiece.getPositionAfterEatingNeighbor(eatenPiece);
        if (landingTile == null) {
            return;
        }
        double weight = 2;
        if (eatenPiece.isKing()) {
            weight += 1f / step;
        }
        if (eatingPiece.isKing()) {
            weight *= EATING_KING_MULTIPLIER;
        }
        addEdge(eatingPiece.getBlackTile(), landingTile, weight);
        Move move = game.makeAndSaveMove(eatingPiece, List.of(eatingPiece.getBlackTile(), landingTile));
        if (move.hasPromoted()) {
            game.undoLastMove();
            return;
        }
        List<BlackTile> eatableNeighbors = landingTile.getPiece().getReachableNeighboringBlackTiles()
                .filter(blackTileNeighbor -> !blackTileNeighbor.isEmpty() && landingTile.getPiece().canEatNeighbor(blackTileNeighbor.getPiece()))
                .collect(Collectors.toList());
        for (BlackTile blackTileNeighbor : eatableNeighbors) {
            recursivelyAddEatingEdges(landingTile.getPiece(), blackTileNeighbor.getPiece(), ++step);
        }
        game.undoLastMove();
    }

    void explorePossibleMoves() {
        DijkstraShortestPath<BlackTile, Edge> dijkstra = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<BlackTile, Edge> paths = dijkstra.getPaths(source);
        longestPaths.addAll(possibleDestinations.stream().map(paths::getPath).collect(getLongestPathsCollector()));
    }


    public void printVertices(){
        Set<BlackTile> tiles = graph.vertexSet();
        tiles.forEach(System.out::println);
    }

    public void printEdges(){
        Set<Edge> edges = graph.edgeSet();
        edges.forEach(System.out::println);
    }

    public List<GraphPath<BlackTile, Edge>> getLongestPaths() {
        return longestPaths;
    }

    public double getMaxPathWeight(){
        return longestPaths.get(0).getWeight();
    }

    static Collector<GraphPath<BlackTile, Edge>, List<GraphPath<BlackTile, Edge>>, List<GraphPath<BlackTile, Edge>>> getLongestPathsCollector() {
        Comparator<GraphPath<BlackTile, Edge>> comparator = Comparator.comparingDouble(GraphPath::getWeight);
        return Collector.of(
                ArrayList::new,
                (list, path) -> {
                    int result;
                    if (list.isEmpty() || (result = comparator.compare(path, list.get(0))) == 0) {
                        list.add(path);
                    } else if (result > 0) {
                        list.clear();
                        list.add(path);
                    }
                },
                (list1, list2) -> {
                    if (list1.isEmpty()) {
                        return list2;
                    }
                    if (list2.isEmpty()) {
                        return list1;
                    }
                    int result = comparator.compare(list1.get(0), list2.get(0));
                    if (result < 0) {
                        return list2;
                    } else if (result > 0) {
                        return list1;
                    } else {
                        list1.addAll(list2);
                        return list1;
                    }
                }
        );
    }

}
