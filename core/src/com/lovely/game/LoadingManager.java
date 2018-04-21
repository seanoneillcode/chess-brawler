package com.lovely.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SoundLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.HashMap;
import java.util.Map;

public class LoadingManager {

    static final String PAWN_IDLE = "pawn-idle.png";
    static final String CASTLE_IDLE = "castle-idle.png";
    static final String KNIGHT_IDLE = "knight-idle.png";
    static final String BISHOP_IDLE = "bishop-idle.png";
    static final String QUEEN_IDLE = "queen-idle.png";
    static final String KING_IDLE = "king-idle.png";
    static final String TILE = "tile.png";
    static final String TILE_ACTIVE = "tile-active.png";
    static final String TILE_TAKING = "tile-taking.png";
    static final String CURSOR = "cursor.png";

    private Map<String, Animation<TextureRegion>> anims;
    private AssetManager assetManager;

    public LoadingManager() {
        assetManager = new AssetManager();
        FileHandleResolver fileHandleResolver = new InternalFileHandleResolver();
        assetManager.setLoader(Texture.class, new TextureLoader(fileHandleResolver));
        assetManager.setLoader(Sound.class, new SoundLoader(fileHandleResolver));
        this.anims = new HashMap<>();
    }

    void load() {
        assetManager.load(PAWN_IDLE, Texture.class);
        assetManager.load(CASTLE_IDLE, Texture.class);
        assetManager.load(KNIGHT_IDLE, Texture.class);
        assetManager.load(BISHOP_IDLE, Texture.class);
        assetManager.load(QUEEN_IDLE, Texture.class);
        assetManager.load(KING_IDLE, Texture.class);
        assetManager.load(TILE, Texture.class);
        assetManager.load(TILE_ACTIVE, Texture.class);
        assetManager.load(TILE_TAKING, Texture.class);
        assetManager.load(CURSOR, Texture.class);
        assetManager.finishLoading();
        processAnimation(PAWN_IDLE, 1, 1f);
        processAnimation(CASTLE_IDLE, 1, 1f);
        processAnimation(KNIGHT_IDLE, 1, 1f);
        processAnimation(BISHOP_IDLE, 1, 1f);
        processAnimation(QUEEN_IDLE, 1, 1f);
        processAnimation(KING_IDLE, 1, 1f);
        processAnimation(TILE, 1, 1f);
        processAnimation(TILE_ACTIVE, 1, 1f);
        processAnimation(TILE_TAKING, 1, 1f);
        processAnimation(CURSOR, 1, 1f);
    }

    public Animation<TextureRegion> getAnimation(String name) {
        return anims.get(name);
    }

    private void processAnimation(String fileName, int numberOfFrames, float frameDelay) {
        Texture sheet = assetManager.get(fileName);
        TextureRegion[][] tmp = TextureRegion.split(sheet, sheet.getWidth() / numberOfFrames, sheet.getHeight());
        Array<TextureRegion> frames = new Array<>(numberOfFrames);
        for (int i = 0; i < numberOfFrames; i++) {
            frames.add(tmp[0][i]);
        }
        anims.put(fileName, new Animation<>(frameDelay, frames));
    }

    public void dispose() {
        assetManager.dispose();
    }
}
