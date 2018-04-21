package com.lovely.game;

import com.badlogic.gdx.math.Vector2;

public class Effect {

    String image;
    float timer;
    Vector2 pos;
    float offsetTimer;
    float animTimer;

    public Effect(String image, float timer, Vector2 pos) {
        this.image = image;
        this.timer = timer;
        this.pos = pos;
        this.offsetTimer = 0;
        this.animTimer = 0;
    }

    public Effect(String image, float timer, Vector2 pos, float offsetTimer) {
        this.image = image;
        this.timer = timer;
        this.pos = pos;
        this.offsetTimer = offsetTimer;
        this.animTimer = 0;
    }
}
