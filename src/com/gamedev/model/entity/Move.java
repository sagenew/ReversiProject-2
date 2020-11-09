package com.gamedev.model.entity;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return row == move.row &&
                col == move.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
