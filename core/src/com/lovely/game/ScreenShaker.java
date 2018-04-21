package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class ScreenShaker {

    private static final float DURATION = 1.2f;
    private static final float REDUCTION = 0.9f;
    private float amount;
    private boolean isActive;
    private float timer;

    ScreenShaker() {
        isActive = false;
    }

    public void update() {
        if (isActive) {
            timer = timer - Gdx.graphics.getDeltaTime();
            amount = (amount * REDUCTION);
            if (timer < 0) {
                amount = 0;
                timer = 0;
                isActive = false;
            }
        }
    }

    public Vector2 getShake() {
        return new Vector2(MathUtils.random(-amount, amount), MathUtils.random(-amount, amount));
    }

    public void shake(float shakeAmount) {
        timer = DURATION;
        isActive = true;
        amount = shakeAmount;
    }
}
