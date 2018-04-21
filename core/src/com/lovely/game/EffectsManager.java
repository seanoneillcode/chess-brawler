package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.List;

import static com.lovely.game.LoadingManager.EXPLOSION;

public class EffectsManager {

    private static final float EXPLOSION_DURATION = 0.64f;
    List<Effect> effects;

    EffectsManager() {
        effects = new ArrayList<>();
    }

    void update() {
        for (Effect effect : effects) {
            if (effect.offsetTimer < 0) {
                effect.timer = effect.timer - Gdx.graphics.getDeltaTime();
                effect.animTimer = effect.animTimer + Gdx.graphics.getDeltaTime();
            } else {
                effect.offsetTimer = effect.offsetTimer - Gdx.graphics.getDeltaTime();
            }
        }
        effects.removeIf(effect -> effect.timer < 0);
    }

    public void blowUpPlayer(String player, ChessBrawler context) {
        for (Piece piece : context.pieceManager.pieces) {
            if (piece.owner.equals(player)) {
                effects.add(new Effect(EXPLOSION, EXPLOSION_DURATION, piece.pos.cpy(), MathUtils.random(0.6f)));
            }
        }
    }
}
