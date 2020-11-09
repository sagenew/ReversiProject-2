package com.gamedev.player.strategy.minimax.tree;

import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.Player;
import com.gamedev.player.strategy.minimax.MinimaxStrategy;

import java.util.Comparator;
import java.util.List;

public class MoveNode {
    private int[][] gameBoard;
    private Player player;

    private int value;
    private int level;
    private Move move;
    private List<MoveNode> nextMoves;
    private MoveNode bestNextMove;

    public MoveNode() { }

    public MoveNode(int[][] board, Move move, int level, Player player) {
        this.gameBoard = board;
        this.move = move;
        this.level = level;
        this.player = player;
    }

    public int[][] getGameBoard() {
        return gameBoard;
    }

    public Move getMove() {
        return move;
    }

    public List<MoveNode> getNextMoves() {
        return nextMoves;
    }

    public void setNextMoves(List<MoveNode> nextMoves) {
        this.nextMoves = nextMoves;
    }

    public int getValue() {
        return value;
    }

    public int getLevel() {
        return level;
    }

    public Player getPlayerColour() {
        return player;
    }

    public void computeValue() {
        if (nextMoves == null || nextMoves.isEmpty()) {
            value = MinimaxStrategy.evaluateMove(gameBoard, move, player);
        } else {
            bestNextMove = level % 2 == 0
                    ? nextMoves.stream()
                        .max(Comparator.comparing(MoveNode::getValue))
                        .orElse(null)
                    : nextMoves.stream()
                        .min(Comparator.comparing(MoveNode::getValue))
                        .orElse(null);

            value = bestNextMove.getValue();

        }
    }

    public Move getBestNextMove() {
        return bestNextMove.getMove();
    }
}
