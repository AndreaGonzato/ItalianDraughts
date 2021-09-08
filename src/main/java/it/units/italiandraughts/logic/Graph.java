package it.units.italiandraughts.logic;

import org.jgrapht.GraphPath;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static it.units.italiandraughts.logic.StaticUtil.matrixToStream;

public class Graph {

    public static final float EATING_KING_MULTIPLIER = 1.2f;
    SimpleDirectedWeightedGraph<Tile, Edge> graph;
    Tile source;
    List<Tile> possibleDestinations;

    public Graph(Board board, Tile tile) {
        graph = new SimpleDirectedWeightedGraph<>(Edge.class);
        source = tile;
        possibleDestinations = new ArrayList<>();
        addVertices(board);
    }

    private void addVertices(Board board){
        matrixToStream(board.getTiles()).filter(tile -> tile instanceof BlackTile)
                .forEach(graph::addVertex);
    }

    void addEdge(Tile source, Tile target, double weight){
        Edge e1 = graph.addEdge(source, target);
        graph.setEdgeWeight(e1, weight);
        possibleDestinations.add(target);
    }

    void addEatingEdges(Piece eatingPiece, Piece eatenPiece, int step) {
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
        addEdge(eatingPiece.getTile(), landingTile, weight);
        landingTile.placePiece(new Piece(eatingPiece.getPieceColor(),
                eatingPiece.getPieceType(), landingTile));
        List<BlackTile> eatableNeighbors = landingTile.getPiece().getNeighborsThisPieceCanMoveTowards()
                .filter(neighbor -> landingTile.getPiece().canEatNeighbor(neighbor.getPiece()))
                .collect(Collectors.toList());
        for (BlackTile neighbor : eatableNeighbors) {
            addEatingEdges(landingTile.getPiece(), neighbor.getPiece(), ++step);
        }
        landingTile.removePiece();
    }

    void explorePossibleMoves() {
        DijkstraShortestPath<Tile, Edge> dijkstra = new DijkstraShortestPath<>(graph);
        ShortestPathAlgorithm.SingleSourcePaths<Tile, Edge> paths = dijkstra.getPaths(source);
        Supplier<Stream<GraphPath<Tile, Edge>>> supplier = () -> possibleDestinations.stream().map(paths::getPath);
        Optional<GraphPath<Tile, Edge>> optionalLongestPath = supplier.get().min((path1, path2) -> {
            return (int) (path2.getWeight() - path1.getWeight()); // TODO improve this?
        });
        optionalLongestPath.ifPresent(longestPath -> supplier.get().filter(path -> path.getWeight() == longestPath.getWeight())
                // TODO remove these diagnostic prints
                .forEach(path -> {
                }));
    }

    public void printVertices(){
        Set<Tile> tiles = graph.vertexSet();
        tiles.forEach(System.out::println);
    }

    public void printEdges(){
        Set<Edge> edges = graph.edgeSet();
        edges.forEach(System.out::println);
    }
}
