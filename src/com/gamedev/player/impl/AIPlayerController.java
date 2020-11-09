package com.gamedev.player.impl;

import com.gamedev.model.ReversiGame;
import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.Player;
import com.gamedev.player.PlayerController;
import com.gamedev.player.strategy.AbstractStrategy;

public class AIPlayerController implements PlayerController {
    ReversiGame reversiGame;
    Player player;
    AbstractStrategy strategy;

    public AIPlayerController(AbstractStrategy strategy, ReversiGame reversiGame, Player player) {
        this.reversiGame = reversiGame;
        this.player = player;
        this.strategy = strategy;
    }

    @Override
    public Move getNextMove() {
        return strategy.chooseNextMove(reversiGame.getGameBoardCopy(), getPlayerColour());
    }

    @Override
    public Player getPlayerColour() {
        return player;
    }
}
