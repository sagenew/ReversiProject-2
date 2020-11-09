package com.gamedev.controller;

import com.gamedev.player.PlayerController;
import com.gamedev.player.impl.AIPlayerController;
import com.gamedev.player.impl.UserPlayerController;
import com.gamedev.model.ReversiGame;
import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.Player;
import com.gamedev.player.strategy.minimax.MinimaxStrategy;

import java.util.Scanner;

public class Controller {
    private Scanner scanner;
    private ReversiGame reversiGame;
    private PlayerController aiController;
    private PlayerController opponentController;

    private int moveCounter;

    public Controller() {
        scanner = new Scanner(System.in);
        reversiGame = new ReversiGame();
    }

    public void init() {
        initBlackholePosition();
        initPlayers();
        startGameLoop();
    }

    private void initBlackholePosition() {
        String blackholePosition = scanner.nextLine();
        Move blackHole = stringToMove(blackholePosition);
        reversiGame.setBlackHole(blackHole);
    }

    private void initPlayers() {
        Player aiColour;
        Player opponentColour;
        String aiColourInput = scanner.nextLine();

        if (aiColourInput.equals("white")) {
            reversiGame.placeDisc(stringToMove(scanner.nextLine()));
            aiColour = Player.WHITE;
            opponentColour = Player.BLACK;
        } else if (aiColourInput.equals("black")) {
            aiColour = Player.BLACK;
            opponentColour = Player.WHITE;
        } else throw new RuntimeException("No valid colour specified");

        aiController = new AIPlayerController(new MinimaxStrategy(), reversiGame, aiColour);
        opponentController = new UserPlayerController(reversiGame, opponentColour);
    }

    private void startGameLoop() {
        Move nextMove;
        while (reversiGame.gameNotFinished()) {
            if (moveCounter % 2 == 0) {
                nextMove = aiController.getNextMove();
                System.out.println(moveToString(nextMove));
            } else {
                nextMove = opponentController.getNextMove();
            }
            moveCounter++;
            reversiGame.placeDisc(nextMove);
        }
    }

    private Move stringToMove(String line) {
        line = line.toUpperCase();
        return new Move(line.charAt(1) - '1', line.charAt(0) - 'A');
    }

    private String moveToString(Move move) {
        if (move == null) return "pass";
        String s = "";
        char row = (char) move.getRow();
        row += '1';
        char col = (char) move.getCol();
        col += 'A';
        s = s.concat(String.valueOf(col)).concat(String.valueOf(row));
        return s;
    }
}
