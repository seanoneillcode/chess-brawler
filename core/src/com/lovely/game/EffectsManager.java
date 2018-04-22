package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lovely.game.LoadingManager.*;

public class EffectsManager {

    List<Effect> effects;
    List<String> bloodImages = Arrays.asList(BLOOD_1, BLOOD_2);

    EffectsManager() {
        effects = new ArrayList<>();
    }

    void start() {
        effects.clear();
    }

    void update() {
        for (Effect effect : effects) {
            effect.animTimer = effect.animTimer + Gdx.graphics.getDeltaTime();
        }
    }

    public void blowUpPlayer(String player, ChessBrawler context) {
        for (Piece piece : context.pieceManager.pieces) {
            if (piece.owner.equals(player)) {
                effects.add(new Effect(EXPLOSION, piece.pos.cpy()));
            }
        }
    }

    public void addBlood(Vector2 pos) {
        effects.add(new Effect(bloodImages.get(MathUtils.random(bloodImages.size() - 1)), pos.cpy()));
        effects.add(new Effect(BLOOD_3, pos.cpy().add(0, 4)));
    }
}
