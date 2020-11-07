package com.gamedev.player.impl;

import com.gamedev.player.Player;
import com.gamedev.model.Model;
import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.PlayerColour;
import java.util.ArrayList;

public class AIPlayer implements Player {
    Model model;
    PlayerColour playerColour;

    public AIPlayer(Model model, PlayerColour player) {
        this.model = model;
        this.playerColour = player;
    }

    @Override
    public Move getNextMove() {
        return getRandomPossibleMove(playerColour);
    }

    @Override
    public PlayerColour getPlayerColour() {
        return playerColour;
    }

    private Move getRandomPossibleMove(PlayerColour currentPlayer) {
        ArrayList<Move> movesList = new ArrayList<>(model.getPossibleMoves(getPlayerColour()));
        if (movesList.isEmpty()) return null;
        int randomIndex = (int)(Math.random() * movesList.toArray().length);
        return movesList.get(randomIndex);
    }
}
