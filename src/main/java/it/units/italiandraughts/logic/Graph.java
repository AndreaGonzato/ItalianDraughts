package it.units.italiandraughts.logic;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class Graph {

    private static final float EATING_KING_MULTIPLIER = 1.2f;
    private final SimpleDirectedWeightedGraph<BlackTile, Edge> graph;
    private final BlackTile source;
    private final List<BlackTile> possibleDestinations;
    private final List<GraphPath<BlackTile, Edge>> longestPaths;
    private final Game game;

    public Graph(BlackTile source, Game game) {
        graph = new SimpleDirectedWeightedGraph<>(Edge.class);
        this.source = source;
        this.game = game;
        possibleDestinations = new ArrayList<>();
        addVertices(game.getBoard());
        longestPaths = new ArrayList<>();
    }

    private void addVertices(Board board){
        matrixToStream(board.getTiles()).filter(tile -> tile instanceof BlackTile)
                .map(BlackTile::asBlackTile)
                .forEach(graph::addVertex);
    }

    void addEdge(BlackTile source, BlackTile target, double weight){
        Edge e1 = graph.addEdge(source, target);
        graph.setEdgeWeight(e1, weight);
        possibleDestinations.add(target);
    }

    void recursivelyAddEatingEdges(Piece eatingPiece, Piece eatenPiece, int step) {
        BlackTile landingTile = eatingPiece.getPositionAfterEating(eatenPiece);
        if (landingTile == null) {
            return;
        }
        double weight = 2;
        if (eatenPiece.getPieceType().equals(PieceType.KING)) {
            weight += 1f / step;
        }
        if (eatingPiece.getPieceType().equals(PieceType.KING)) {
            weight *= EATING_KING_MULTIPLIER;
        }
        addEdge(eatingPiece.getBlackTile(), landingTile, weight);
        game.moveStepByStep(eatingPiece, List.of(eatingPiece.getBlackTile(), landingTile), true);
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
        longestPaths.addAll(possibleDestinations.stream().map(paths::getPath).collect(StaticUtil.getLongestPaths()));
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
}
