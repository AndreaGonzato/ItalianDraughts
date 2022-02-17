package it.units.italiandraughts.logic;

import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.PieceType;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;

@ExtendWith(ApplicationExtension.class)
public class DijkstraGraphTest {

    private final float epsilon = (float) 10e-6;

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
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        DijkstraGraph graph = new DijkstraGraph(BlackTile.asBlackTile(board.getTiles()[6][2]), game);
        int possibleMoves = 1;
        int longestPathsWeight = 1;
        Assertions.assertTrue(
                graph.getLongestPaths().size() == possibleMoves &&
                        Math.abs(graph.getLongestPaths().get(0).getWeight() - longestPathsWeight) < epsilon &&
                        graph.getLongestPaths().get(0).getEndVertex().equals(board.getTiles()[5][1])
        );
    }

    @Test
    void explorePossibleMovesFromBlackTile2() {
        Board board = prepareBoard();
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        DijkstraGraph graph = new DijkstraGraph(BlackTile.asBlackTile(board.getTiles()[5][3]), game);
        int possibleMoves = 1;
        int longestPathsWeight = 2;
        Assertions.assertTrue(
                graph.getLongestPaths().size() == possibleMoves &&
                        Math.abs(graph.getLongestPaths().get(0).getWeight() - longestPathsWeight) < epsilon &&
                        graph.getLongestPaths().get(0).getEndVertex().equals(board.getTiles()[7][1])
        );
    }

    @Test
    void explorePossibleMovesFromBlackTile3() {
        Board board = prepareBoard();
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        DijkstraGraph graph = new DijkstraGraph(BlackTile.asBlackTile(board.getTiles()[5][5]), game);
        int possibleMoves = 1;
        double longestPathsWeight = 4 * 1.2f;
        Assertions.assertTrue(
                graph.getLongestPaths().size() == possibleMoves &&
                        Math.abs(graph.getLongestPaths().get(0).getWeight() - longestPathsWeight) < epsilon &&
                        graph.getLongestPaths().get(0).getEndVertex().equals(board.getTiles()[1][1])
        );
    }

    @Test
    void explorePossibleMovesFromBlackTile4() {
        Board board = prepareBoard();
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        DijkstraGraph graph = new DijkstraGraph(BlackTile.asBlackTile(board.getTiles()[4][4]), game);
        int possibleMoves = 0;
        Assertions.assertEquals(graph.getLongestPaths().size(), possibleMoves);
    }

}
