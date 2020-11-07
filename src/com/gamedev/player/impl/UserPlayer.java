package com.gamedev.player.impl;

import com.gamedev.player.Player;
import com.gamedev.model.Model;
import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.PlayerColour;
import java.util.Scanner;

public class UserPlayer implements Player {
    Model model;
    PlayerColour playerColour;
    Scanner scanner;

    public UserPlayer(Model model, PlayerColour playerColour) {
        this.model = model;
        this.playerColour = playerColour;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Move getNextMove() {
        String moveString = scanner.nextLine();
        return new Move(moveString.charAt(1) - '1', moveString.charAt(0) - 'A');
    }

    @Override
    public PlayerColour getPlayerColour() {
        return playerColour;
    }
}