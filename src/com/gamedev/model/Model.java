package com.gamedev.model;

import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.PlayerColour;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Model {
    private int[][] board;
    private PlayerColour currentPlayer;
    private final int BOARD_SIZE = 8;
    private final int EMPTY_DISC = 0;
    private final int WHITE_DISC = 1;
    private final int BLACK_DISC = 2;
    private final int POSSIBLE_MOVE = 3;
    private final int BLACK_HOLE = 4;
    public Model() {
        initGame();
        currentPlayer = PlayerColour.BLACK;
    }

    public void initGame() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        placeStartDiscs();
    }

    private void placeStartDiscs() {
        board[3][3] = WHITE_DISC;
        board[3][4] = BLACK_DISC;
        board[4][3] = BLACK_DISC;
        board[4][4] = WHITE_DISC;
    }

    public void placeDisc(Move move) {
        if(move != null) {
            int playerDisc = currentPlayer == PlayerColour.BLACK ? BLACK_DISC : WHITE_DISC;
            int opponentDisc = currentPlayer == PlayerColour.BLACK ? WHITE_DISC : BLACK_DISC;
            board[move.getRow()][move.getCol()] = playerDisc;

            int[] directionsX = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] directionsY = {-1, 0, 1, -1, 1, -1, 0, 1};
            for (int i = 0; i < directionsX.length; i++) {
                flipDiscsInDirection(playerDisc, opponentDisc, move, directionsX[i], directionsY[i]);
            }
        }
        changeCurrentPlayer();
    }

    private void flipDiscsInDirection(int player, int opponent, Move move, int directionX, int directionY) {
        int i = move.getRow() + directionX;
        int j = move.getCol() + directionY;

        Set<Move> toFlip = new HashSet<>();

        while (inBounds(i, j)) {
            if (board[i][j] == opponent) {
                toFlip.add(new Move(i, j));
            } else if (board[i][j] == player) {
                break;
            } else {
                toFlip.clear();
                break;
            }
            i = i + directionX;
            j = j + directionY;
        }

        if (!inBounds(i, j)) {
            toFlip.clear();
        }

        for (Move markedMove : toFlip) {
            board[markedMove.getRow()][markedMove.getCol()] = player;
        }
    }

    private boolean inBounds(int i, int j) {
        return ((i >= 0) && (i < 8) && (j >= 0) && (j < 8));
    }

    private void changeCurrentPlayer() {
        currentPlayer = currentPlayer == PlayerColour.BLACK
                ? PlayerColour.WHITE
                : PlayerColour.BLACK;
    }

    public Set<Move> getPossibleMoves(PlayerColour player) {
        Set<Move> moves = new HashSet<>();
        int playerDisc = (player == PlayerColour.BLACK) ? BLACK_DISC : WHITE_DISC;
        int opponentDisc = (player == PlayerColour.BLACK) ? WHITE_DISC : BLACK_DISC;
        for (int row = 0; row < BOARD_SIZE; row++)
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] != EMPTY_DISC) continue;

                int[] directionsX = {-1, -1, -1, 0, 0, 1, 1, 1};
                int[] directionsY = {-1, 0, 1, -1, 1, -1, 0, 1};
                for (int i = 0; i < directionsX.length; i++) {
                    if ((hasOpponentDiscInDirection(row + directionsX[i], col + directionsY[i], opponentDisc))
                            && hasPlayerDiscInDirection(row, col, directionsX[i], directionsY[i], playerDisc))
                        moves.add(new Move(row, col));
                }
            }
        return moves;
    }

    private boolean hasOpponentDiscInDirection(int row, int col, int opponentDisc) {
        if ((row < 0 || row >= BOARD_SIZE) || (col < 0 || col >= BOARD_SIZE)) return false;
        return board[row][col] == opponentDisc;
    }

    private boolean hasPlayerDiscInDirection(int row, int col, int directionX, int directionY, int playerDisc) {
        int x = row + directionX;
        int y = col + directionY;
        while (true) {
            x += directionX;
            y += directionY;
            if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE
                    || board[x][y] == EMPTY_DISC || board[x][y] == BLACK_HOLE) break;
            if (board[x][y] == playerDisc) return true;
        }
        return false;
    }

    public void setBlackHole(Move blackHole) {
        board[blackHole.getRow()][blackHole.getCol()] = BLACK_HOLE;
    }

    public Move getBlackHole() {
        for(int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                if (board[i][j] == BLACK_HOLE) return new Move(i,j);
                return null;
    }

    public int[][] getGameBoard(boolean showHints) {
        if (!showHints) return board;

        int[][] boardCopy = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);;
        Set<Move> moves = getPossibleMoves(currentPlayer);
        moves.forEach((move) -> boardCopy[move.getRow()][move.getCol()] = POSSIBLE_MOVE);
        return boardCopy;
    }

    public PlayerColour getCurrentPlayer() {
        return currentPlayer;
    }
}
