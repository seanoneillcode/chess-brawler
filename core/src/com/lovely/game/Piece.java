package com.lovely.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Piece {

    Vector2 pos;
    Vector2 mov;
    PieceType type;
    State state;
    String idleImage, walkImage, dieImage;
    String owner;
    public float moveTimer;
    boolean isLocked;
    public float animTimer;
    AnimState animState;
    public float dieTimer;

    public Piece(Vector2 pos, Vector2 mov, PieceType type, String idleImage, String owner, String walkImage, String dieImage) {
        this.pos = pos;
        this.mov = mov;
        this.type = type;
        this.owner = owner;
        this.idleImage = idleImage;
        state = State.ALIVE;
        moveTimer = 0;
        animTimer = MathUtils.random(2.0f);
        isLocked = false;
        animState = AnimState.IDLE;
        dieTimer = 0;
        this.walkImage = walkImage;
        this.dieImage = dieImage;
    }

    enum AnimState {
        IDLE,
        WALK,
        DIE
    }

    enum State {
        ALIVE,
        DYING,
        DEAD
    }
}
