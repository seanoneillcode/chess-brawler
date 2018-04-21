package com.lovely.game;

public class Move {
    int x;
    int y;
    boolean taking;
    int value;

    public Move(int x, int y) {
        this.x = x;
        this.y = y;
        taking = false;
        this.value = 0;
    }

    public Move(int x, int y, boolean taking, int value) {
        this.x = x;
        this.y = y;
        this.taking = taking;
        this.value = value;
    }
}
