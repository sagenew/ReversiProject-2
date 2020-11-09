package com.gamedev.player.strategy.minimax.tree;

import com.gamedev.model.ReversiGame;
import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MoveTree {
    private MoveNode rootNode;
    private int level;
    private int depth;

    public MoveTree(int depth) {
        this.rootNode = new MoveNode();
        this.depth = depth;
        this.level = 0;
    }

    public void initTree(int[][] gameBoard, Player player) {
        level++;
        ReversiGame reversiGame = new ReversiGame(gameBoard, player);
        List<Move> possibleMoves = new ArrayList<>(reversiGame.getPossibleMoves(player));
        transformMovesToNodes(rootNode, reversiGame.getGameBoardCopy(),
                possibleMoves, level, player);
    }

    public void fillTree() {
        for (MoveNode node : rootNode.getNextMoves()) {
            parseNode(node, level + 1);
            node.computeValue();
        }
        rootNode.computeValue();
    }

    private void parseNode(MoveNode node, int level) {
        if (node.getLevel() >= depth) return;

        ReversiGame reversiGame = new ReversiGame(node.getGameBoard(), node.getPlayerColour());
        reversiGame.placeDisc(node.getMove());
        List<Move> possibleNextMoves = new ArrayList<>(reversiGame.getPossibleMoves(reversiGame.getCurrentPlayer()));

        if (possibleNextMoves.isEmpty()) return;

        List<MoveNode> nodes = transformMovesToNodes(node, reversiGame.getGameBoardCopy(),
                possibleNextMoves, level, reversiGame.getCurrentPlayer());

        for (MoveNode nextNode : nodes) {
            parseNode(nextNode, level + 1);
            nextNode.computeValue();
        }
    }

    private List<MoveNode> transformMovesToNodes(MoveNode node, int[][] gameBoard, List<Move> moves, int nodeLevel, Player player) {
        List<MoveNode> nodes = moves.stream()
                .map(move -> new MoveNode(gameBoard, move, nodeLevel, player))
                .collect(Collectors.toList());
        node.setNextMoves(nodes);

        return nodes;
    }

    public Move getBestMove() {
        return rootNode.getBestNextMove();
    }
}
