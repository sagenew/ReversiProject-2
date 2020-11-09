package com.gamedev.player.strategy;

import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.Player;

public interface AbstractStrategy {
    Move chooseNextMove(int[][] board, Player player);
}
