package com.gamedev.player.impl;

import com.gamedev.model.ReversiGame;
import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.Player;
import com.gamedev.player.PlayerController;

import java.util.Scanner;

public class UserPlayerController implements PlayerController {
    ReversiGame reversiGame;
    Player player;
    Scanner scanner;

    public UserPlayerController(ReversiGame reversiGame, Player player) {
        this.reversiGame = reversiGame;
        this.player = player;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public Move getNextMove() {
        String moveString = "";
        while (moveString.equals("")) {
            moveString = scanner.nextLine();
        }
        if(moveString.equals("pass")) return null;
        return new Move(moveString.charAt(1) - '1', moveString.charAt(0) - 'A');
    }

    @Override
    public Player getPlayerColour() {
        return player;
    }
}
