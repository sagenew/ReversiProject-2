package com.gamedev.controller;

public class ConsoleView {
    public void printGameBoard(int[][] gameBoard) {
        System.out.println("    A    B    C    D    E    F    G    H    ");
        printHorizontalLine();
        for (int row = 0; row < gameBoard.length; row++) {
            System.out.print(row + 1);
            System.out.print(" | ");
            for (int col = 0; col < gameBoard[row].length; col++) {
                switch (gameBoard[row][col]) {
                    case 0 -> System.out.printf("   %c ", '|');
                    case 1 -> System.out.printf("%s %c ", getWhiteDisc(), '|');
                    case 2 -> System.out.printf("%s %c ", getBlackDisc(), '|');
                    case 3 -> System.out.printf("%c %c ", '◯', '|');
                    case 4 -> System.out.printf("%s %c ", " #", '|');
                }
            }
            System.out.print("\n");
            printHorizontalLine();
        }
    }

    public void printGameBoardSimple(int[][] gameBoard) {
        for (int row = 0; row < gameBoard.length; row++) {
            for (int col = 0; col < gameBoard[row].length; col++) {
                System.out.print(gameBoard[row][col] + " ");
            }
            System.out.print("\n");
        }
    }

    private String getBlackDisc() {
        if (System.getProperty("os.name").equals("Linux")) return "🔴";
        else return " x";
    }

    private String getWhiteDisc() {
        if (System.getProperty("os.name").equals("Linux")) return "🔵";
        else return " o";
    }

    public void printHorizontalLine() {
        System.out.println("  +----+----+----+----+----+----+----+----+");
    }
}
