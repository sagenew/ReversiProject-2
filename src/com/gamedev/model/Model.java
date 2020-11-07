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
    public Model() {
        initGame();
        currentPlayer = PlayerColour.BLACK;
    }

    public void initGame() {
        board = new int[BOARD_SIZE][BOARD_SIZE];
        placeStartDiscs();
    }

    private void placeStartDiscs() {
        board[3][3] = 1;
        board[3][4] = 2;
        board[4][3] = 2;
        board[4][4] = 1;
    }

    public void placeDisc(Move move) {
        int playerDisc = currentPlayer == PlayerColour.BLACK ? 2 : 1;
        int opponentDisc = currentPlayer == PlayerColour.BLACK ? 1 : 2;
        board[move.getRow()][move.getCol()] = playerDisc;

        int[] directionsX = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] directionsY = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (int i = 0; i < directionsX.length; i++) {
            flipDiscsInDirection(playerDisc, opponentDisc, move, directionsX[i], directionsY[i]);
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
        if (currentPlayer == PlayerColour.BLACK) currentPlayer = PlayerColour.WHITE;
        else currentPlayer = PlayerColour.BLACK;
    }

    public Set<Move> getPossibleMoves(PlayerColour player) {
        Set<Move> moves = new HashSet<>();
        int playerDisc = (player == PlayerColour.BLACK) ? 2 : 1;
        int opponentDisc = (player == PlayerColour.BLACK) ? 1 : 2;
        for (int row = 0; row < BOARD_SIZE; row++)
            for (int col = 0; col < BOARD_SIZE; col++) {
                if (board[row][col] != 0) continue;

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
            if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE || board[x][y] == 0) break;
            if (board[x][y] == playerDisc) return true;
        }
        return false;
    }

    public void setBlackHole(Move blackHole) {
        board[blackHole.getRow()][blackHole.getCol()] = 4;
    }

    public Move getBlackHole() {
        for(int i = 0; i < BOARD_SIZE; i++)
            for (int j = 0; j < BOARD_SIZE; j++)
                if (board[i][j] == 4) return new Move(i,j);
                return null;
    }

    public int[][] getGameBoard(boolean showHints) {
        if (!showHints) return board;

        int[][] boardCopy = Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);;
        Set<Move> moves = getPossibleMoves(currentPlayer);
        moves.forEach((move) -> boardCopy[move.getRow()][move.getCol()] = 3);
        return boardCopy;
    }

    public PlayerColour getCurrentPlayer() {
        return currentPlayer;
    }
}
