package com.lovely.game;

public enum PieceType {

    PAWN(1),
    CASTLE(5),
    BISHOP(3),
    KING(100),
    QUEEN(9),
    KNIGHT(3);

    int value;

    PieceType(int value) {
        this.value = value;
    }
}
