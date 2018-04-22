package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.lovely.game.Constants.VIEWPORT_HEIGHT;
import static com.lovely.game.Constants.VIEWPORT_WIDTH;
import static com.lovely.game.LoadingManager.*;

public class LevelManager {

    List<Drawable> grass;
    List<String> grassImages = Arrays.asList(GRASS_1, GRASS_2, GRASS_3, GRASS_4);
    List<String> shadowCloudImages = Arrays.asList(CLOUD_1, CLOUD_2, CLOUD_3);
    List<String> cloudImages = Arrays.asList(CLOUD_4, CLOUD_5, CLOUD_6);
    List<Drawable> clouds;
    List<Drawable> shadowsClouds;
    List<Drawable> menuClouds;

    LevelManager() {
        grass = new ArrayList<>();
        clouds = new ArrayList<>();
        shadowsClouds = new ArrayList<>();
        menuClouds = new ArrayList<>();
    }

    void menuStart() {
        for (int i = 0; i < 8; i++) {
            Vector2 pos = new Vector2(MathUtils.random(-100, 200), MathUtils.random(-20, 120));
            Vector2 mov = new Vector2(MathUtils.random(2.0f, 8.0f), 0);
            menuClouds.add(new Drawable(cloudImages.get(MathUtils.random(0, cloudImages.size() - 1)), pos, MathUtils.random(1.0f), mov));
        }
    }

    void start() {
        grass.clear();
        shadowsClouds.clear();
        clouds.clear();
        // GRASS
        // lleft
        for (int i = 0; i < 10; i++) {
            Vector2 pos = new Vector2(MathUtils.random(-60, 0), MathUtils.random(-16, VIEWPORT_HEIGHT - 70));
            grass.add(new Drawable(grassImages.get(MathUtils.random(0, grassImages.size() - 1)), pos, MathUtils.random(1.0f)));
        }
        // midel
        for (int i = 0; i < 4; i++) {
            Vector2 pos = new Vector2(MathUtils.random(0, 160), MathUtils.random(-16, VIEWPORT_HEIGHT - 70));
            grass.add(new Drawable(grassImages.get(MathUtils.random(0, grassImages.size() - 1)), pos, MathUtils.random(1.0f)));
        }
        // right
        for (int i = 0; i < 10; i++) {
            Vector2 pos = new Vector2(MathUtils.random(160, VIEWPORT_WIDTH - 40), MathUtils.random(-16, VIEWPORT_HEIGHT - 70));
            grass.add(new Drawable(grassImages.get(MathUtils.random(0, grassImages.size() - 1)), pos, MathUtils.random(1.0f)));
        }

        // CLOUDS
        // SHADOWS
        for (int i = 0; i < 8; i++) {
            Vector2 pos = new Vector2(MathUtils.random(0, 300) - 120, MathUtils.random(0, 100) - 20);
            Vector2 mov = new Vector2(MathUtils.random(4.0f, 8.0f), -1f * MathUtils.random(2f));
            shadowsClouds.add(new Drawable(shadowCloudImages.get(MathUtils.random(0, shadowCloudImages.size() - 1)), pos, MathUtils.random(1.0f), mov));
        }
        // BACKGROUND

        for (int i = 0; i < 4; i++) {
            Vector2 pos = new Vector2(MathUtils.random(-100, 200), MathUtils.random(80, 120) + 40);
            Vector2 mov = new Vector2(MathUtils.random(2.0f, 4.0f), 0);
            clouds.add(new Drawable(cloudImages.get(MathUtils.random(0, cloudImages.size() - 1)), pos, MathUtils.random(1.0f), mov));
        }
    }

    void update() {
        for (Drawable drawable : grass) {
            updateDrawable(drawable);
        }
        for (Drawable drawable : clouds) {
            updateDrawable(drawable);
        }
        for (Drawable drawable : shadowsClouds) {
            updateDrawable(drawable);
        }
        for (Drawable drawable : menuClouds) {
            updateDrawable(drawable);
        }
    }

    void updateDrawable(Drawable drawable) {
        drawable.animTimer += Gdx.graphics.getDeltaTime();
        drawable.pos.add(drawable.mov.cpy().scl(Gdx.graphics.getDeltaTime()));
        if (drawable.pos.x > VIEWPORT_WIDTH + 20) {
            drawable.pos.x = -200;
        }
    }
}
