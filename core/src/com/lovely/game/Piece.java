package com.lovely.game;

import com.badlogic.gdx.math.Vector2;

public class Piece {

    Vector2 pos;
    Vector2 mov;
    PieceType type;
    State state;
    String image;
    String owner;
    public float moveTimer;
    boolean isLocked;

    public Piece(Vector2 pos, Vector2 mov, PieceType type, String image, String owner) {
        this.pos = pos;
        this.mov = mov;
        this.type = type;
        this.owner = owner;
        this.image = image;
        state = State.ALIVE;
        moveTimer = 0;
        isLocked = false;
    }

    enum State {
        ALIVE,
        DEAD
    }
}
