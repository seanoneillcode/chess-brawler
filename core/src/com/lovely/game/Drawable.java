package com.lovely.game;

import com.badlogic.gdx.math.Vector2;

public class Drawable {
    String image;
    Vector2 pos;
    float animTimer;
    Vector2 mov;

    public Drawable(String image, Vector2 pos, float animTimer) {
        this.image = image;
        this.pos = pos;
        this.animTimer = animTimer;
        mov = new Vector2();
    }

    public Drawable(String image, Vector2 pos, float animTimer, Vector2 mov) {
        this.image = image;
        this.pos = pos;
        this.animTimer = animTimer;
        this.mov = mov;
    }
}
