package com.lovely.game;

import com.badlogic.gdx.math.Vector2;

public class Effect {

    String image;
//    float timer;
    Vector2 pos;
    float animTimer;

    public Effect(String image, Vector2 pos) {
        this.image = image;
//        this.timer = timer;
        this.pos = pos;
        this.animTimer = 0;
    }
}
