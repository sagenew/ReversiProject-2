package com.gamedev.player;

import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.Player;

public interface PlayerController {
    Move getNextMove();

    Player getPlayerColour();
}
