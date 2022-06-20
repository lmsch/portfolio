/* GameBoard:
an abstraction of a tic-tac-toe game. Uses a two dimensional array to check for win or tie states.
Keeps track of the current turn, computes which player goes first, and provides helper methods for the main activity.
 */

package com.example.android.tic_tac_toe;

public class GameBoard {

    public static final int BOARD_SIZE = 3;
    //player one always plays with 'x'
    public static final String PLAYER_ONE_SYMBOL = "X", PLAYER_TWO_SYMBOL = "O";
    private String[][] board;
    private String currentTurn;
    private int turnCount;

    public GameBoard() {
        board = new String[BOARD_SIZE][BOARD_SIZE];
        turnCount = 0;
    }

    /*
    Separate constructor for app reconfiguration.
     */
    public GameBoard(String [][] board, String currentTurn, int turnCount) {
        this.board = board;
        this.currentTurn = currentTurn;
        this.turnCount = turnCount;
    }

    /*
    Chooses randomly the player who will make the first turn.
     */
    public void computeFirstTurn() {
        int firstTurn = (int)(Math.random() * 2) + 1;
        if(firstTurn == 1)
            currentTurn = PLAYER_ONE_SYMBOL;
        else
            currentTurn = PLAYER_TWO_SYMBOL;
    }

    public void setBlock(int row, int col){
        board[row][col] = currentTurn;
    }


    public boolean isOpen(int row, int col) {
        return board[row][col] == null;
    }

    public String getSpotValue(int row, int col) {
        return board[row][col];
    }

    public void changeTurn() {
        if(currentTurn == PLAYER_ONE_SYMBOL)
            currentTurn = PLAYER_TWO_SYMBOL;
        else
            currentTurn = PLAYER_ONE_SYMBOL;
        turnCount++;
    }

    public int getTurnPlayerNumber() {
        if(currentTurn == PLAYER_ONE_SYMBOL)
            return 1;
        else
            return 2;
    }

    public String getTurnSymbol() {
        return currentTurn;
    }

    public boolean isTied() {
        return turnCount == BOARD_SIZE * BOARD_SIZE - 1;
    }

    /*
    Checks if game is one using four helper methods
    These methods will scan horizontally, vertically, diagonally for a row with 3 identical symbols
     */
    public boolean isWon(int row, int col) {
        return isHorizontalFull(row) || isVerticalFull(col) || isLeftDiagonalFull() || isRightDiagonalFull();
    }

    /*
    Following two methods scan the corresponding row or col to check if all symbols belong to the current player making their turn
     */
    private boolean isHorizontalFull(int row) {
        for(int j = 0; j < BOARD_SIZE; j++)
            if (!isSymbolOfCurrentTurn(row, j))
                return false;
        return true;
    }
    private boolean isVerticalFull(int col) {
        for(int i = 0; i < BOARD_SIZE; i++)
            if (!isSymbolOfCurrentTurn(i, col))
                return false;
        return true;
    }

    /*
    A similar algorithm is used for diagonals. Plays off the fact that the row and col values are related to each other.
     */
    private boolean isLeftDiagonalFull() {
        for(int i = 0; i < BOARD_SIZE; i++)
                if(!isSymbolOfCurrentTurn(i, i))
                    return false;
        return true;
    }

    private boolean isRightDiagonalFull() {
        for(int i = BOARD_SIZE - 1; i >= 0; i--) {
            int j = BOARD_SIZE - 1 - i;
            if (!isSymbolOfCurrentTurn(i, j))
                return false;
        }
        return true;
    }

    private boolean isSymbolOfCurrentTurn(int row, int col) {
        return board[row][col] == currentTurn;
    }

    public String[][] getBoard() {
        return board;
    }

    public String getCurrentTurn() {
        return currentTurn;
    }

    public int getTurnCount() {
        return turnCount;
    }
}
