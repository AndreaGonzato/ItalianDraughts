package it.units.italiandraughts.logic;

import it.units.italiandraughts.ui.SquareType;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.Iterator;

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
                if (tile.getSquare().getType().equals(SquareType.BRONZE)){
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
        Tile start = graph
                .vertexSet().stream().findAny()
                .get();

        Iterator<Tile> iterator = new DepthFirstIterator<>(graph, start);
        while (iterator.hasNext()) {
            Tile tile = iterator.next();
            System.out.println(tile);
        }
    }
}
