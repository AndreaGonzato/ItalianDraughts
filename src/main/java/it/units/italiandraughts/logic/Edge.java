package it.units.italiandraughts.logic;

import org.jgrapht.graph.DefaultWeightedEdge;

public class Edge extends DefaultWeightedEdge {

    public Edge() {
        super();
    }

    @Override
    public String toString() {
        return Double.toString(getWeight());
    }


}
