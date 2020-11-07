package com.gamedev.model.entity;

public class Move {
    int row;
    int col;

    public Move(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    public String toString () {
        return String.format("[%s, %s]", row, col);
    }
}
