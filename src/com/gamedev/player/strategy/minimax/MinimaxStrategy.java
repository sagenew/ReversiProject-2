package com.gamedev.player.strategy.minimax;

import com.gamedev.model.ReversiGame;
import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.Player;
import com.gamedev.player.strategy.AbstractStrategy;
import com.gamedev.player.strategy.minimax.tree.MoveTree;


/**
 * Formula to calculate move efficiency was based on strategies for Reversed Reversi (source link below)
 * @see <a href="http://samsoft.org.uk/reversi/strategy.htm">Reversed Reversi Strategy</a>
 */
public class MinimaxStrategy implements AbstractStrategy {

    private static final int[][] positionalValuesTable = new int[][]{
                        { -1000, 100, -150, -100 },
                        {  100, -200, 20, -20 },
                        {  -150, 20,  -15, -15 },
                        {   -100,   -20,  -15, -10 }
    };

    @Override
    public Move chooseNextMove(int[][] board, Player player) {
        ReversiGame reversiGame = new ReversiGame(board, player);
        if (reversiGame.getPossibleMoves(player).isEmpty()) return null;

        MoveTree moveTree = new MoveTree(1);
        moveTree.initTree(board, player);
        moveTree.fillTree();

        return moveTree.getBestMove();
    }

    public static int evaluateMove(int[][] gameBoard, Move move, Player player) {
        ReversiGame reversiGame = new ReversiGame(gameBoard, player);
        reversiGame.placeDisc(move);
        return //10 * positionalValue(reversiGame.getGameBoardCopy(), player)
                //+ 25 * frontierValue(reversiGame, player)
                + 50 * positionalValue(move)
             // + 10000 * reversiGame.getStableDiscsNumber(player)
        ;
    }

    private static int positionalValue(int[][] gameBoard, Player player) {
        int score = 0;
        int playerDisc = (player == Player.BLACK) ? 2 : 1;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j] == playerDisc) {
                    int x = i > 3 ? 7 - i : i;
                    int y = j > 3 ? 7 - j : j;

                    score += positionalValuesTable[x][y];
                }
            }
        }
        return score;
    }

    private static int positionalValue(Move move) {
        int x = move.getRow() > 3 ? 7 - move.getRow() : move.getRow();
        int y = move.getCol() > 3 ? 7 - move.getCol() : move.getCol();

        return positionalValuesTable[x][y];
    }

    private static int mobilityValue(ReversiGame reversiGame, Player player) {
        return reversiGame.getPossibleMoves(player).size();
    }

    private static int frontierValue(ReversiGame reversiGame, Player player) {
        return -(reversiGame.getFrontierDiscsNumber(player));
    }
}
