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
//        view.printGameBoardSimple(model.getGameBoard(false));
//        view.printGameBoard(model.getGameBoard(false));
        initPlayers();
        startGameLoop();
    }

    private void initBlackHolePos() {
        String blackHolePos = scanner.nextLine();
//        System.out.println(blackHolePos);
        blackHolePos = extractStringFromLogMessage(blackHolePos);
//        System.out.println(blackHolePos);
        Move blackHole = stringToMove(blackHolePos);
        model.setBlackHole(blackHole);
//        System.out.println(model.getBlackHole());
    }

    private void initPlayers() {
        PlayerColour player1Colour;
        PlayerColour player2Colour;
        String player1ColourString = scanner.nextLine();
//        System.out.println(player1ColourString);
        player1ColourString = extractStringFromLogMessage(player1ColourString);
        if(player1ColourString.equals("White")) {
            player1Colour = PlayerColour.WHITE;
            player2Colour = PlayerColour.BLACK;
        }
        else if(player1ColourString.equals("Black")) {
            player1Colour = PlayerColour.BLACK;
            player2Colour = PlayerColour.WHITE;
        }
        else throw new RuntimeException();
        player1 = new AIPlayer(model, player1Colour);
//        System.out.println("PLAYER 1 " + player1.getPlayerColour());
        player2 = new UserPlayer(model, player2Colour);
//        System.out.println("PLAYER 2 " + player2.getPlayerColour());
    }

    private void startGameLoop() {
        Move nextMove;
        while (true) {
//            view.printGameBoard(model.getGameBoard(true));
            if (model.getCurrentPlayer() == player1.getPlayerColour()) {
                nextMove = player1.getNextMove();
                System.out.println(moveToString(nextMove));
//                System.out.println(player1.getPlayerColour() + " : " + nextMove.toString());
            } else {
                nextMove = player2.getNextMove();
//                System.out.println(player2.getPlayerColour() + " : " + nextMove.toString());
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

    private String extractStringFromLogMessage(String message) {
        String extractedString = message.substring(message.lastIndexOf(":") + 1).trim();
//        System.out.println(extractedString);
         return extractedString;
    }
}
