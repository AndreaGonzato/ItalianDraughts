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

    private static final float EATING_KING_MULTIPLIER = 1.2f;
    private final SimpleDirectedWeightedGraph<Tile, Edge> graph;
    private final Tile source;
    private final List<Tile> possibleDestinations;
    private final List<GraphPath<Tile, Edge>> maxPaths;

    public Graph(Board board, Tile source) {
        graph = new SimpleDirectedWeightedGraph<>(Edge.class);
        this.source = source;
        possibleDestinations = new ArrayList<>();
        addVertices(board);
        maxPaths = new ArrayList();
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
        landingTile.placePiece(new Piece(eatingPiece.getPieceColor(),
                eatingPiece.getPieceType(), landingTile));
        List<BlackTile> eatableNeighbors = landingTile.getPiece().getReachableNeighborsBlackTiles()
                .filter(blackTileNeighbor -> !blackTileNeighbor.isEmpty() && landingTile.getPiece().canEatNeighbor(blackTileNeighbor.getPiece()))
                .collect(Collectors.toList());
        for (BlackTile blackTileNeighbor : eatableNeighbors) {
            recursivelyAddEatingEdges(landingTile.getPiece(), blackTileNeighbor.getPiece(), ++step);
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
                .forEach(maxPaths::add));
                // TODO remove these diagnostic prints
                /*
                .forEach(path -> {

                    System.out.println("Allowed move: FROM");
                    System.out.println(source);
                    System.out.println("TO");
                    System.out.println(path.getEndVertex());
                    System.out.println("WEIGHT = " + path.getWeight());
                    System.out.println();

                }));

                 */
        System.out.println();
    }


    public void printVertices(){
        Set<Tile> tiles = graph.vertexSet();
        tiles.forEach(System.out::println);
    }

    public void printEdges(){
        Set<Edge> edges = graph.edgeSet();
        edges.forEach(System.out::println);
    }

    public List<GraphPath<Tile, Edge>> getMaxPaths() {
        return maxPaths;
    }

    public double getMaxPathWeight(){
        return maxPaths.get(0).getWeight();
    }
}
