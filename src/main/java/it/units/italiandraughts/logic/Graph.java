package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.SquareType;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.Set;

public class Graph {

    SimpleDirectedWeightedGraph<Tile, Edge>  graph;

    public Graph(Board board) {
        graph = new SimpleDirectedWeightedGraph<>(Edge.class);
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

    public void addEdge(Tile source, Tile target, double weight){
        Edge e1 = graph.addEdge(source, target);
        graph.setEdgeWeight(e1, weight);
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
