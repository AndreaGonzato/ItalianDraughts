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
public class GraphTest {

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
        Graph graph = new Graph(BlackTile.asBlackTile(board.getTiles()[6][2]), game);
        int moves = 1;
        int weight = 1;
        Assertions.assertTrue(
                graph.getLongestPaths().size() == moves &&
                        Math.abs(graph.getLongestPaths().get(0).getWeight() - weight) < 10e-6 &&
                        graph.getLongestPaths().get(0).getEndVertex().equals(board.getTiles()[5][1])
        );
    }

    @Test
    void explorePossibleMovesFromBlackTile2() {
        Board board = prepareBoard();
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        Graph graph = new Graph(BlackTile.asBlackTile(board.getTiles()[5][3]), game);
        int moves = 1;
        int weight = 2;
        Assertions.assertTrue(
                graph.getLongestPaths().size() == moves &&
                        Math.abs(graph.getLongestPaths().get(0).getWeight() - weight) < 10e-6 &&
                        graph.getLongestPaths().get(0).getEndVertex().equals(board.getTiles()[7][1])
        );
    }

    @Test
    void explorePossibleMovesFromBlackTile3() {
        Board board = prepareBoard();
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        Graph graph = new Graph(BlackTile.asBlackTile(board.getTiles()[5][5]), game);
        int moves = 1;
        double weight = 4 * Graph.EATING_KING_MULTIPLIER;
        Assertions.assertTrue(
                graph.getLongestPaths().size() == moves &&
                        Math.abs(graph.getLongestPaths().get(0).getWeight() - weight) < 10e-6 &&
                        graph.getLongestPaths().get(0).getEndVertex().equals(board.getTiles()[1][1])
        );
    }

    @Test
    void explorePossibleMovesFromBlackTile4() {
        Board board = prepareBoard();
        Game game = new Game(new Player("", PieceColor.WHITE), new Player("", PieceColor.BLACK));
        Graph graph = new Graph(BlackTile.asBlackTile(board.getTiles()[4][4]), game);
        int moves = 0;
        Assertions.assertEquals(graph.getLongestPaths().size(), moves);
    }

}
