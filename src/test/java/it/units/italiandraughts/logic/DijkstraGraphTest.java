package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.PieceType;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

public class DijkstraGraphTest {

    private static final double EPSILON = 10e-6;

    private Board prepareBoard() {
        Board board = Board.reset();
        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[6][2]);
        blackTile1.placePiece(new WhitePiece());
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[5][3]);
        blackTile2.placePiece(new BlackPiece());
        BlackTile blackTile3 = BlackTile.asBlackTile(board.getTiles()[5][5]);
        blackTile3.placePiece(new WhitePiece(PieceType.KING));
        BlackTile blackTile4 = BlackTile.asBlackTile(board.getTiles()[4][4]);
        blackTile4.placePiece(new BlackPiece());
        BlackTile blackTile5 = BlackTile.asBlackTile(board.getTiles()[3][1]);
        blackTile5.placePiece(new WhitePiece());
        BlackTile blackTile6 = BlackTile.asBlackTile(board.getTiles()[2][2]);
        blackTile6.placePiece(new BlackPiece());
        return board;
    }

    @Test
    void explorePossibleMovesFromBlackTile1() {
        Board board = prepareBoard();
        DijkstraGraph graph = new DijkstraGraph(BlackTile.asBlackTile(board.getTiles()[6][2]));
        int possibleMoves = 1;
        int longestPathsWeight = 1;
        Assertions.assertTrue(
                graph.calculateLongestPaths().size() == possibleMoves &&
                        Math.abs(graph.calculateLongestPaths().get(0).getWeight() - longestPathsWeight) < EPSILON &&
                        graph.calculateLongestPaths().get(0).getEndVertex().equals(board.getTiles()[5][1])
        );
    }

    @Test
    void explorePossibleMovesFromBlackTile2() {
        Board board = prepareBoard();
        DijkstraGraph graph = new DijkstraGraph(BlackTile.asBlackTile(board.getTiles()[5][3]));
        int possibleMoves = 1;
        int longestPathsWeight = 2;
        Assertions.assertTrue(
                graph.calculateLongestPaths().size() == possibleMoves &&
                        Math.abs(graph.calculateLongestPaths().get(0).getWeight() - longestPathsWeight) < EPSILON &&
                        graph.calculateLongestPaths().get(0).getEndVertex().equals(board.getTiles()[7][1])
        );
    }

    @Test
    void explorePossibleMovesFromBlackTile3() {
        Board board = prepareBoard();
        DijkstraGraph graph = new DijkstraGraph(BlackTile.asBlackTile(board.getTiles()[5][5]));
        int possibleMoves = 1;
        double longestPathsWeight = 4 * 1.2;
        Assertions.assertTrue(
                graph.calculateLongestPaths().size() == possibleMoves &&
                        Math.abs(graph.calculateLongestPaths().get(0).getWeight() - longestPathsWeight) < EPSILON &&
                        graph.calculateLongestPaths().get(0).getEndVertex().equals(board.getTiles()[1][1])
        );
    }

    @Test
    void explorePossibleMovesFromBlackTile4() {
        Board board = prepareBoard();
        DijkstraGraph graph = new DijkstraGraph(BlackTile.asBlackTile(board.getTiles()[4][4]));
        int possibleMoves = 0;
        Assertions.assertEquals(graph.calculateLongestPaths().size(), possibleMoves);
    }

}
