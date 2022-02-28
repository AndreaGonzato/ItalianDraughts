package it.units.italiandraughts.logic;

import it.units.italiandraughts.event.EventType;
import it.units.italiandraughts.event.GameEvent;
import it.units.italiandraughts.event.GameEventListener;
import it.units.italiandraughts.exception.IllegalButtonClickException;
import it.units.italiandraughts.exception.InvalidPlayersException;
import it.units.italiandraughts.logic.piece.BlackPiece;
import it.units.italiandraughts.logic.piece.Piece;
import it.units.italiandraughts.logic.piece.PieceType;
import it.units.italiandraughts.logic.piece.WhitePiece;
import it.units.italiandraughts.logic.tile.BlackTile;
import it.units.italiandraughts.ui.PieceColor;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.util.List;

public class GameTest {

    private static final double EPSILON = 10e-6;

    @Test
    void firstActivePlayer() {
        Player whitePlayer = new Player("Player1", PieceColor.WHITE);
        Player blackPlayer = new Player("Player1", PieceColor.BLACK);

        Game game = new Game(blackPlayer, whitePlayer);
        Player activePlayer = game.getActivePlayer();

        Assertions.assertEquals(whitePlayer, activePlayer);
    }

    @Test
    void thatTwoPayerCanNotHaveTheSamePieceColor() {
        Player whitePlayer1 = new Player("Player1", PieceColor.WHITE);
        Player whitePlayer2 = new Player("Player2", PieceColor.WHITE);

        Assertions.assertThrows(InvalidPlayersException.class, () -> new Game(whitePlayer1, whitePlayer2));

    }

    @Test
    void makeAndSaveMoveWithSimpleMove() {
        Board board = Board.reset();
        board.initPieces();
        Game game = initGame();

        BlackTile sourceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile destinationBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        Piece piece = sourceBlackTile.getPiece();
        Move expectedMove = new Move(piece, List.of(sourceBlackTile, destinationBlackTile));
        expectedMove.make();
        expectedMove.undo();
        Move actualMove = game.makeAndSaveMove(piece, List.of(sourceBlackTile, destinationBlackTile));

        Assertions.assertEquals(expectedMove, actualMove);
    }

    @Test
    void checkThatMakeAndSaveMoveAddTheMoveInList() {
        Board board = Board.reset();
        board.initPieces();
        Game game = initGame();

        BlackTile sourceBlackTile = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile destinationBlackTile = BlackTile.asBlackTile(board.getTiles()[3][3]);
        Piece piece = sourceBlackTile.getPiece();
        Move expectedMove = new Move(piece, List.of(sourceBlackTile, destinationBlackTile));
        expectedMove.make();
        expectedMove.undo();
        int movesSizeBefore = game.getMoves().size();
        game.makeAndSaveMove(piece, List.of(sourceBlackTile, destinationBlackTile));
        int movesSizeAfter = game.getMoves().size();

        Assertions.assertEquals(1, movesSizeAfter - movesSizeBefore);
    }

    @Test
    void makeAndSaveMoveEatingOnePiece() {
        Board board = Board.reset();
        Game game = initGame();

        BlackTile source = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile over = BlackTile.asBlackTile(board.getTiles()[3][3]);
        Piece pieceToMove = new BlackPiece();
        source.placePiece(pieceToMove);
        over.placePiece(new WhitePiece());
        Move expectedMove = new Move(pieceToMove, List.of(source, destination));
        expectedMove.make();
        expectedMove.undo();
        Move actualMove = game.makeAndSaveMove(pieceToMove, List.of(source, destination));
        Assertions.assertEquals(expectedMove, actualMove);
    }

    @Test
    void makeAndSaveMoveEatingTwoPieces() {
        Board board = Board.reset();
        Game game = initGame();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[0][0]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[4][0]);
        BlackTile intermediate = BlackTile.asBlackTile(board.getTiles()[2][2]);
        BlackTile over1 = BlackTile.asBlackTile(board.getTiles()[1][1]);
        BlackTile over2 = BlackTile.asBlackTile(board.getTiles()[3][1]);
        Piece pieceToMove = new BlackPiece(PieceType.KING);
        source.placePiece(pieceToMove);
        over1.placePiece(new WhitePiece());
        over2.placePiece(new WhitePiece());
        Move expectedMove = new Move(pieceToMove, List.of(source, intermediate, destination));
        expectedMove.make();
        expectedMove.undo();
        Move actualMove = game.makeAndSaveMove(pieceToMove, List.of(source, intermediate, destination));
        Assertions.assertEquals(expectedMove, actualMove);
    }

    @Test
    void moveActivePieceTo() {
        Game game = initGame();
        Board board = Board.getBoard();
        BlackTile sourceBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);
        BlackTile overBlackTile = BlackTile.asBlackTile(board.getTiles()[4][6]);
        BlackTile destinationBlackTile = BlackTile.asBlackTile(board.getTiles()[3][7]);
        sourceBlackTile.placePiece(new WhitePiece());
        overBlackTile.placePiece(new BlackPiece());
        game.updateAbsoluteLongestPaths();
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER);
        game.addListeners(EventType.GAME_OVER);
        game.setActiveTile(sourceBlackTile);
        game.moveActivePieceTo(destinationBlackTile);
        Assertions.assertTrue(
                sourceBlackTile.isEmpty() && !destinationBlackTile.isEmpty()
                        && overBlackTile.isEmpty()
        );
    }

    @Test
    void checkThatMoveActivePieceToToggleActivePlayer() {
        Board board = Board.reset();
        board.initPieces();
        BlackTile sourceBlackTile = BlackTile.asBlackTile(board.getTiles()[5][1]);
        BlackTile destinationBlackTile = BlackTile.asBlackTile(board.getTiles()[4][0]);
        Player player1 = new Player("player1", PieceColor.WHITE);
        Player player2 = new Player("player2", PieceColor.BLACK);
        Game game = new Game(player1, player2);
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER);
        game.addListeners(EventType.GAME_OVER);
        game.setActiveTile(sourceBlackTile);
        Player activePlayerBeforeMove = game.getActivePlayer();
        game.moveActivePieceTo(destinationBlackTile);
        Player activePlayerAfterMove = game.getActivePlayer();
        Assertions.assertNotEquals(activePlayerBeforeMove, activePlayerAfterMove);
    }

    @Test
    void undoLastMoveFail() {
        Game game = initGame();
        Assertions.assertThrows(IllegalButtonClickException.class, game::undoLastMove);
    }

    @Test
    void undoLastSimpleMove() {
        Game game = initGame();
        Board board = Board.getBoard();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[5][5]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[4][4]);
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER);
        game.setActiveTile(source);
        game.moveActivePieceTo(destination);
        game.undoLastMove();
        Assertions.assertTrue(!source.isEmpty() && destination.isEmpty());
    }

    @Test
    void undoLastEatingMoveRestorePiecePositions() {
        Game game = initGame();
        Board board = Board.getBoard();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[5][5]);
        BlackTile over = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[3][3]);
        over.placePiece(new BlackPiece());
        game.updateAbsoluteLongestPaths();
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER);
        game.setActiveTile(source);
        game.moveActivePieceTo(destination);
        game.undoLastMove();
        Assertions.assertTrue(!source.isEmpty() && destination.isEmpty() && !over.isEmpty());
    }

    @Test
    void undoCheckToToggleActivePlayer() {
        Game game = initGame();
        Board board = Board.getBoard();
        board.initPieces();
        BlackTile source = BlackTile.asBlackTile(board.getTiles()[5][5]);
        BlackTile over = BlackTile.asBlackTile(board.getTiles()[4][4]);
        BlackTile destination = BlackTile.asBlackTile(board.getTiles()[3][3]);
        over.placePiece(new BlackPiece());
        game.updateAbsoluteLongestPaths();

        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER);
        game.setActiveTile(source);
        game.moveActivePieceTo(destination);
        Player formerActivePlayer = game.getActivePlayer();
        game.undo();
        Assertions.assertNotEquals(formerActivePlayer, game.getActivePlayer());
    }


    @Test
    public void checkToNotifyGameOver() {
        Player whitePlayer = new Player("Player1", PieceColor.WHITE);
        Player blackPlayer = new Player("Player2", PieceColor.BLACK);
        Game game = new Game(whitePlayer, blackPlayer);

        Board board = Board.reset();
        BlackTile sourceBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);
        sourceBlackTile.placePiece(new WhitePiece());
        BlackTile overBlackTile = BlackTile.asBlackTile(board.getTiles()[4][6]);
        overBlackTile.placePiece(new BlackPiece());
        BlackTile destinationBackTile = BlackTile.asBlackTile(board.getTiles()[3][7]);

        Listener listener = new Listener();
        game.updateAbsoluteLongestPaths();
        game.addListeners(EventType.GAME_OVER, listener);
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER, listener);
        game.setActiveTile(sourceBlackTile);

        game.moveActivePieceTo(destinationBackTile);

        Assertions.assertTrue(listener.receivedGameOver);
    }

    @Test
    public void checkWinnerPlayer() {

        Player whitePlayer = new Player("Player1", PieceColor.WHITE);
        Player blackPlayer = new Player("Player2", PieceColor.BLACK);
        Game game = new Game(whitePlayer, blackPlayer);
        Board board = Board.reset();

        BlackTile sourceBlackTile = BlackTile.asBlackTile(board.getTiles()[5][5]);
        sourceBlackTile.placePiece(new WhitePiece());
        BlackTile overBlackTile = BlackTile.asBlackTile(board.getTiles()[4][6]);
        overBlackTile.placePiece(new BlackPiece());
        BlackTile destinationBackTile = BlackTile.asBlackTile(board.getTiles()[3][7]);
        Listener listener = new Listener();
        game.updateAbsoluteLongestPaths();
        game.addListeners(EventType.GAME_OVER, listener);
        game.addListeners(EventType.SWITCH_ACTIVE_PLAYER, listener);
        game.setActiveTile(sourceBlackTile);

        game.moveActivePieceTo(destinationBackTile);

        Assertions.assertEquals(game.getWinnerPlayer(), whitePlayer);
    }

    @Test
    void updateAbsoluteLongestPathsOnNewGame() {
        Game game = initGame();
        game.updateAbsoluteLongestPaths();
        int paths = 7;
        int weight = 1;
        BlackTile blackTile1 = BlackTile.asBlackTile(Board.getBoard().getTiles()[4][0]);
        int pathsEndingOnBlackTile1 = 1;
        BlackTile blackTile2 = BlackTile.asBlackTile(Board.getBoard().getTiles()[4][2]);
        int pathsEndingOnBlackTile2 = 2;
        BlackTile blackTile3 = BlackTile.asBlackTile(Board.getBoard().getTiles()[4][4]);
        int pathsEndingOnBlackTile3 = 2;
        BlackTile blackTile4 = BlackTile.asBlackTile(Board.getBoard().getTiles()[4][6]);
        int pathsEndingOnBlackTile4 = 2;
        List<GraphPath<BlackTile, DefaultWeightedEdge>> absoluteLongestPaths = game.getAbsoluteLongestPaths();
        Assertions.assertTrue(
                absoluteLongestPaths.size() == paths &&
                        absoluteLongestPaths.get(0).getWeight() - weight < EPSILON &&
                        absoluteLongestPaths.get(1).getWeight() - weight < EPSILON &&
                        absoluteLongestPaths.get(2).getWeight() - weight < EPSILON &&
                        absoluteLongestPaths.get(3).getWeight() - weight < EPSILON &&
                        absoluteLongestPaths.get(4).getWeight() - weight < EPSILON &&
                        absoluteLongestPaths.get(5).getWeight() - weight < EPSILON &&
                        absoluteLongestPaths.get(6).getWeight() - weight < EPSILON &&
                        absoluteLongestPaths.stream().filter(path -> path.getEndVertex().equals(blackTile1)).count() == pathsEndingOnBlackTile1 &&
                        absoluteLongestPaths.stream().filter(path -> path.getEndVertex().equals(blackTile2)).count() == pathsEndingOnBlackTile2 &&
                        absoluteLongestPaths.stream().filter(path -> path.getEndVertex().equals(blackTile3)).count() == pathsEndingOnBlackTile3 &&
                        absoluteLongestPaths.stream().filter(path -> path.getEndVertex().equals(blackTile4)).count() == pathsEndingOnBlackTile4
        );
    }

    @Test
    void updateAbsoluteLongestPathsInArtificialScenario() {
        Game game = initGame();
        Board board = Board.reset();
        BlackTile blackTile1 = BlackTile.asBlackTile(board.getTiles()[5][5]);
        blackTile1.placePiece(new WhitePiece(PieceType.KING));
        BlackTile blackTile2 = BlackTile.asBlackTile(board.getTiles()[4][4]);
        blackTile2.placePiece(new BlackPiece());
        BlackTile blackTile3 = BlackTile.asBlackTile(board.getTiles()[2][2]);
        blackTile3.placePiece(new BlackPiece());
        BlackTile blackTile4 = BlackTile.asBlackTile(board.getTiles()[6][4]);
        blackTile4.placePiece(new BlackPiece());
        BlackTile blackTile5 = BlackTile.asBlackTile(board.getTiles()[6][2]);
        blackTile5.placePiece(new BlackPiece());

        BlackTile blackTile6 = BlackTile.asBlackTile(board.getTiles()[5][1]);
        BlackTile blackTile7 = BlackTile.asBlackTile(board.getTiles()[1][1]);

        game.updateAbsoluteLongestPaths();
        List<GraphPath<BlackTile, DefaultWeightedEdge>> absoluteLongestPaths = game.getAbsoluteLongestPaths();
        int paths = 2;
        double weight = 4 * 1.2;
        int pathsEndingOnBlackTile6 = 1;
        int pathsEndingOnBlackTile7 = 1;

        Assertions.assertTrue(
                absoluteLongestPaths.size() == paths &&
                        absoluteLongestPaths.get(0).getWeight() - weight < EPSILON &&
                        absoluteLongestPaths.get(1).getWeight() - weight < EPSILON &&
                        absoluteLongestPaths.stream().filter(path -> path.getEndVertex().equals(blackTile6)).count() == pathsEndingOnBlackTile6 &&
                        absoluteLongestPaths.stream().filter(path -> path.getEndVertex().equals(blackTile7)).count() == pathsEndingOnBlackTile7
        );
    }

    private Game initGame() {
        return new Game(new Player("Player1", PieceColor.WHITE), new Player("Player1", PieceColor.BLACK));
    }

    static class Listener implements GameEventListener {

        private boolean receivedGameOver = false;

        @Override
        public void onGameEvent(GameEvent event) {
            if (event.getEventType() == EventType.GAME_OVER) {
                receivedGameOver = true;
            }
        }

    }


}
