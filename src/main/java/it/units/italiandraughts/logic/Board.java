package it.units.italiandraughts.logic;


import java.util.Arrays;

public class Board {
    private int[][] board;
    private final int SIZE = 8;

    public static void main(String[] args) {
        Board b = new Board();
        System.out.println(b);
    }

    Board() {
        board = new int[SIZE][SIZE];

        int player2Pieces = 12;
        int player1Pieces = 12;



        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if ((row + col) % 2 == 0 && player2Pieces > 0) {
                    board[row][col] = 2;
                    player2Pieces--;
                }
            }
        }

        for (int row = 5; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                if ((row + col) % 2 == 0 && player1Pieces > 0) {
                    board[row][col] = 1;
                    player1Pieces--;
                }
            }
        }

    }

    @Override
    public String toString() {
        String result = "Board{ board=\n";
        for (int i=0 ; i<SIZE; i++){
            result += Arrays.toString(board[i]) + "\n";
        }
        result += " }";
        return result;
    }
}
