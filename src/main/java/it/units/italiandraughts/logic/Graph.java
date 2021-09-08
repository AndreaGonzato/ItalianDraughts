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
import java.util.stream.Stream;

public class Graph {

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
        for (int i = 0; i < Board.SIZE; i++) {
            for (int j = 0; j < Board.SIZE; j++) {
                Tile tile = board.getTiles()[i][j];
                if ((tile.getX() + tile.getY()) % 2 == 0){
                    graph.addVertex(tile);
                }
            }
        }
    }

    void addEdge(Tile source, Tile target, double weight){
        Edge e1 = graph.addEdge(source, target);
        graph.setEdgeWeight(e1, weight);
        possibleDestinations.add(target);
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
