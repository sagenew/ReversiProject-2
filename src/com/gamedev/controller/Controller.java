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
    String s;
    Move whiteMove;

    public Controller() {
        scanner = new Scanner(System.in);
        model = new Model();
        view = new ConsoleView();
        s = "";
    }

    public void init() {
        initBlackHolePos();
        initPlayers();

        startGameLoop();
    }

    private void initBlackHolePos() {
        String blackHolePos = scanner.nextLine();
        s += blackHolePos;
        Move blackHole = stringToMove(blackHolePos);
        model.setBlackHole(blackHole);
    }

    private void initPlayers() {
        PlayerColour player1Colour;
        PlayerColour player2Colour;
        String player1ColourString = scanner.nextLine();
        s += " " + player1ColourString;
        if(player1ColourString.equals("white")) {
            whiteMove = stringToMove(scanner.nextLine());
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
//        String whiteMove = scanner.next();
//        s += " " + whiteMove;
        Move nextMove;
//        System.out.println(s);
//        while (player1.hasPossibleMoves() || player2.hasPossibleMoves()) {
        while (true) {
            if (model.getCurrentPlayer() == player1.getPlayerColour()) {
                nextMove = player1.getNextMove();
                System.out.println(moveToString(nextMove));
            } else {
                if(whiteMove != null) {
                    nextMove = whiteMove;
                    whiteMove = null;
                }
                else nextMove = player2.getNextMove();
//                System.out.println(nextMove);
            }
            model.placeDisc(nextMove);
        }

    }

    private Move stringToMove(String line) {
        line = line.toUpperCase();
        return new Move(line.charAt(1) - '1', line.charAt(0) - 'A');
    }

    private String moveToString(Move move) {
        if (move == null) return "pass";
        String s = "";
        char row = (char)move.getRow();
        row += '1';
        char col = (char)move.getCol();
        col += 'A';
        s = s.concat(String.valueOf(col)).concat(String.valueOf(row));
        return s;
    }
}
