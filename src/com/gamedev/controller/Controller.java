package com.gamedev.controller;

import com.gamedev.player.impl.AIPlayer;
import com.gamedev.player.Player;
import com.gamedev.player.impl.UserPlayer;
import com.gamedev.model.Model;
import com.gamedev.model.entity.Move;
import com.gamedev.model.entity.PlayerColour;

import java.util.Scanner;

public class Controller {
    Scanner scanner;
    Model model;
    ConsoleView view;
    Player player1;
    Player player2;


    public Controller() {
        scanner = new Scanner(System.in);
        model = new Model();
        view = new ConsoleView();
    }

    public void init() {
        initBlackHolePos();
        initPlayers();
        startGameLoop();
    }

    private void initBlackHolePos() {
        String blackHolePos = scanner.nextLine();
        Move blackHole = stringToMove(blackHolePos);
        model.setBlackHole(blackHole);
    }

    private void initPlayers() {
        PlayerColour player1Colour;
        PlayerColour player2Colour;
        String player1ColourString = scanner.nextLine();
        if(player1ColourString.equals("white")) {
            player1Colour = PlayerColour.WHITE;
            player2Colour = PlayerColour.BLACK;
        }
        else if(player1ColourString.equals("black")) {
            player1Colour = PlayerColour.BLACK;
            player2Colour = PlayerColour.WHITE;
        }
        else throw new RuntimeException();
        player1 = new AIPlayer(model, player1Colour);
        player2 = new UserPlayer(model, player2Colour);
    }

    private void startGameLoop() {
        Move nextMove;
        while (true) {
            if (model.getCurrentPlayer() == player1.getPlayerColour()) {
                nextMove = player1.getNextMove();
                System.out.println(moveToString(nextMove));
            } else {
                nextMove = player2.getNextMove();
            }
            model.placeDisc(nextMove);
        }
    }

    private Move stringToMove(String line) {
        line = line.toUpperCase();
        return new Move(line.charAt(1) - '1', line.charAt(0) - 'A');
    }

    private String moveToString(Move move) {
        String s = "";
        char row = (char)move.getRow();
        row += '1';
        char col = (char)move.getCol();
        col += 'A';
        s = s.concat(String.valueOf(col)).concat(String.valueOf(row));
        return s;
    }
}
