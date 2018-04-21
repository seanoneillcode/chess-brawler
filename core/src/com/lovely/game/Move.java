package com.lovely.game;

public class Move {
    int x;
    int y;
    boolean taking;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
        taking = false;
    }

    public Move(int x, int y, boolean taking) {
        this.x = x;
        this.y = y;
        this.taking = taking;
    }
}
