package com.gamedev.player;

import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.PlayerColour;

public interface Player {
    public Move getNextMove();
    public PlayerColour getPlayerColour();
    public boolean hasPossibleMoves();
}
