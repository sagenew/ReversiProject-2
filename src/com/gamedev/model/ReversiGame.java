package com.gamedev.model;

import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.Player;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.gamedev.model.entity.Disc.*;

public class ReversiGame {
    private int[][] board;
    private Player currentPlayer;
    private final int BOARD_SIZE = 8;

    public ReversiGame() {
        initGame();
        currentPlayer = Player.BLACK;
    }

    public ReversiGame(int[][] board, Player currentPlayer) {
        this.board = board;
        this.currentPlayer = currentPlayer;
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
        if (move != null) {
            int playerDisc = currentPlayer == Player.BLACK ? BLACK_DISC : WHITE_DISC;
            int opponentDisc = currentPlayer == Player.BLACK ? WHITE_DISC : BLACK_DISC;
            board[move.getRow()][move.getCol()] = playerDisc;

            int[] directionsX = {-1, -1, -1, 0, 0, 1, 1, 1};
            int[] directionsY = {-1, 0, 1, -1, 1, -1, 0, 1};
            for (int i = 0; i < directionsX.length; i++) {
                flipDiscsInDirection(playerDisc, opponentDisc, move, directionsX[i], directionsY[i]);
            }
        }

        checkGameState();
    }

    private void checkGameState() {
        if (getPossibleMoves(Player.BLACK).size() + getPossibleMoves(Player.WHITE).size() == 0) {
            return;
        }

        if (getPossibleMoves(Player.BLACK).size() > 0 && getPossibleMoves(Player.WHITE).size() == 0) {
            currentPlayer = Player.BLACK;
            return;
        }

        if (getPossibleMoves(Player.BLACK).size() == 0 && getPossibleMoves(Player.WHITE).size() > 0) {
            currentPlayer = Player.WHITE;
            return;
        }

        passTurn();
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

    private void passTurn() {
        currentPlayer = currentPlayer == Player.BLACK
                ? Player.WHITE
                : Player.BLACK;
    }

    public Set<Move> getPossibleMoves(Player player) {
        Set<Move> moves = new HashSet<>();
        int playerDisc = (player == Player.BLACK) ? BLACK_DISC : WHITE_DISC;
        int opponentDisc = (player == Player.BLACK) ? WHITE_DISC : BLACK_DISC;
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

    public int getFrontierDiscsNumber(Player player) {
        int frontierDiscs = 0;
        int playerDisc = (player == Player.BLACK) ? BLACK_DISC : WHITE_DISC;
        int opponentDisc = (player == Player.BLACK) ? WHITE_DISC : BLACK_DISC;
        Set<Move> moves = getPlayerDiscs(playerDisc);

        int[] directionsX = {-1, -1, -1, 0, 0, 1, 1, 1};
        int[] directionsY = {-1, 0, 1, -1, 1, -1, 0, 1};
        for (Move move : moves) {
            int row = move.getRow();
            int col = move.getCol();
            for (int i = 0; i < directionsX.length; i++) {
                if ((!hasOpponentDiscInDirection(row + directionsX[i], col + directionsY[i], opponentDisc))
                        && !hasPlayerDiscInDirection(row, col, directionsX[i], directionsY[i], playerDisc))
                    frontierDiscs++;
            }
        }

        return frontierDiscs;
    }

    public int getStableDiscsNumber(Player player) {
        int playerDisc = (player == Player.BLACK) ? BLACK_DISC : WHITE_DISC;

        int playerStableDiscs = 0;

        int[] directionsX = {1, 1, -1, -1};
        int[] directionsY = {1, -1, -1, 1};
        int[][] corners = {{0, 0}, {0, 7}, {7, 7}, {7, 0}};

        for (int i = 0; i < directionsX.length; i++) {
            playerStableDiscs += stableDiscsFromCorner(corners[i], directionsX[i], directionsY[i], playerDisc);
        }

        return playerStableDiscs;
    }

    private int stableDiscsFromCorner(int[] corner, int directionsX, int directionsY, int playerDisc) {
        int stableDiscs = 0;
        for (int i = corner[0]; i < 8 && i > 0; i += directionsX) {
            if (board[i][corner[1]] == playerDisc) {
                for (int j = corner[1]; j < 8 && j > 0; j += directionsY) {
                    if (board[i][j] == playerDisc) {
                        stableDiscs++;
                    } else {
                        break;
                    }
                }
            } else {
                break;
            }
        }
        return stableDiscs;
    }

    private Set<Move> getPlayerDiscs(int playerDisc) {
        Set<Move> moves = new HashSet<>();

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == playerDisc) {
                    moves.add(new Move(i, j));
                }
            }
        }

        return moves;
    }

    public void setBlackHole(Move blackHole) {
        board[blackHole.getRow()][blackHole.getCol()] = BLACK_HOLE;
    }

    public boolean gameNotFinished() {
        return getPossibleMoves(Player.BLACK).size() + getPossibleMoves(Player.WHITE).size() > 0;
    }

    public int[][] getGameBoardCopy() {
        return Arrays.stream(board).map(int[]::clone).toArray(int[][]::new);
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }
}
