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
    static final String PAWN_WALK = "pawn-walk.png";
    static final String PAWN_DIE = "pawn-die.png";
    static final String CASTLE_IDLE = "castle-idle.png";
    static final String CASTLE_WALK = "castle-walk.png";
    static final String CASTLE_DIE = "castle-die.png";
    static final String KNIGHT_IDLE = "knight-idle.png";
    static final String KNIGHT_WALK = "knight-walk.png";
    static final String KNIGHT_DIE = "knight-die.png";
    static final String BISHOP_IDLE = "bishop-idle.png";
    static final String BISHOP_WALK = "bishop-walk.png";
    static final String BISHOP_DIE = "bishop-die.png";
    static final String QUEEN_IDLE = "queen-idle.png";
    static final String QUEEN_DIE = "queen-die.png";
    static final String KING_IDLE = "king-idle.png";
    static final String KING_WALK = "king-walk.png";
    static final String KING_DIE = "king-die.png";
    static final String PAWN_IDLE_RED = "pawn-idle-red.png";
    static final String PAWN_WALK_RED = "pawn-walk-red.png";
    static final String PAWN_DIE_RED = "pawn-die-red.png";
    static final String CASTLE_IDLE_RED = "castle-idle-red.png";
    static final String CASTLE_WALK_RED = "castle-walk-red.png";
    static final String CASTLE_DIE_RED = "castle-die-red.png";
    static final String KNIGHT_IDLE_RED = "knight-idle-red.png";
    static final String KNIGHT_WALK_RED = "knight-walk-red.png";
    static final String KNIGHT_DIE_RED = "knight-die-red.png";
    static final String BISHOP_IDLE_RED = "bishop-idle-red.png";
    static final String BISHOP_WALK_RED = "bishop-walk-red.png";
    static final String BISHOP_DIE_RED = "bishop-die-red.png";
    static final String QUEEN_IDLE_RED = "queen-idle-red.png";
    static final String QUEEN_DIE_RED = "queen-die-red.png";
    static final String KING_IDLE_RED = "king-idle-red.png";
    static final String KING_WALK_RED = "king-walk-red.png";
    static final String KING_DIE_RED = "king-die-red.png";
    static final String EXPLOSION = "explosion.png";
    static final String TILE = "tile.png";
    static final String TILE_MASK = "tile-mask.png";
    static final String TILE_SELECTED = "tile-selected.png";
    static final String TILE_ACTIVE = "tile-active.png";
    static final String TILE_TAKING = "tile-taking.png";
    static final String CLOUD_1 = "cloud-1.png";
    static final String CLOUD_2 = "cloud-2.png";
    static final String CLOUD_3 = "cloud-3.png";
    static final String CLOUD_4 = "cloud-4.png";
    static final String CLOUD_5 = "cloud-5.png";
    static final String CLOUD_6 = "cloud-6.png";
    static final String BLOOD_1 = "blood-1.png";
    static final String BLOOD_2 = "blood-2.png";
    static final String BLOOD_3 = "blood-3.png";
    static final String GRASS_1 = "grass-1.png";
    static final String GRASS_2 = "grass-2.png";
    static final String GRASS_3 = "grass-3.png";
    static final String GRASS_4 = "grass-4.png";
    static final String CURSOR = "cursor.png";
    static final String CURSOR_MOVE = "cursor-move.png";
    static final String CURSOR_ATTACK = "cursor-attack.png";
    static final String GRASS_BACKGROUND = "background-grass.png";
    static final String LOGO = "logo.png";
    static final String PORTRAIT_1 = "portrait-1.png";
    static final String PORTRAIT_2 = "portrait-2.png";
    static final String PORTRAIT_3 = "portrait-3.png";
    static final String PORTRAIT_4 = "portrait-4.png";
    static final String SPEECH_BUBBLE = "speech-bubble.png";

    static final String MUSIC_FIGHT_0 = "sound/fight-0.ogg";
    static final String MUSIC_FIGHT_1 = "sound/fight-1.ogg";
    static final String MUSIC_FIGHT_2 = "sound/fight-2.ogg";
    static final String MUSIC_FIGHT_3 = "sound/fight-3.ogg";
    static final String MUSIC_FAIL = "sound/fail.ogg";
    static final String MUSIC_WIN = "sound/win.ogg";

    static final String SOUND_CLANG_1 = "sound/clang-1.ogg";
    static final String SOUND_CLANG_2 = "sound/clang-2.ogg";
    static final String SOUND_CLANG_3 = "sound/clang-3.ogg";
    static final String SOUND_SCREAM_1 = "sound/scream-1.ogg";
    static final String SOUND_SCREAM_2 = "sound/scream-2.ogg";
    static final String SOUND_SCREAM_3 = "sound/scream-3.ogg";
    static final String SOUND_SCREAM_4 = "sound/scream-4.ogg";
    static final String SOUND_SCREAM_5 = "sound/scream-5.ogg";
    static final String SOUND_ORDER_1 = "sound/order-1.ogg";
    static final String SOUND_ORDER_2 = "sound/order-2.ogg";
    static final String SOUND_ATTACK_1 = "sound/attack-1.ogg";
    static final String SOUND_ATTACK_2 = "sound/attack-2.ogg";
    static final String SOUND_ATTACK_3 = "sound/attack-3.ogg";


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
        assetManager.load(PAWN_WALK, Texture.class);
        assetManager.load(PAWN_DIE, Texture.class);
        assetManager.load(CASTLE_IDLE, Texture.class);
        assetManager.load(CASTLE_WALK, Texture.class);
        assetManager.load(CASTLE_DIE, Texture.class);
        assetManager.load(KNIGHT_IDLE, Texture.class);
        assetManager.load(KNIGHT_WALK, Texture.class);
        assetManager.load(KNIGHT_DIE, Texture.class);
        assetManager.load(BISHOP_IDLE, Texture.class);
        assetManager.load(BISHOP_WALK, Texture.class);
        assetManager.load(BISHOP_DIE, Texture.class);
        assetManager.load(QUEEN_IDLE, Texture.class);
        assetManager.load(QUEEN_DIE, Texture.class);
        assetManager.load(KING_IDLE, Texture.class);
        assetManager.load(KING_WALK, Texture.class);
        assetManager.load(KING_DIE, Texture.class);
        assetManager.load(PAWN_IDLE_RED, Texture.class);
        assetManager.load(PAWN_WALK_RED, Texture.class);
        assetManager.load(PAWN_DIE_RED, Texture.class);
        assetManager.load(CASTLE_IDLE_RED, Texture.class);
        assetManager.load(CASTLE_WALK_RED, Texture.class);
        assetManager.load(CASTLE_DIE_RED, Texture.class);
        assetManager.load(KNIGHT_IDLE_RED, Texture.class);
        assetManager.load(KNIGHT_WALK_RED, Texture.class);
        assetManager.load(KNIGHT_DIE_RED, Texture.class);
        assetManager.load(BISHOP_IDLE_RED, Texture.class);
        assetManager.load(BISHOP_WALK_RED, Texture.class);
        assetManager.load(BISHOP_DIE_RED, Texture.class);
        assetManager.load(QUEEN_IDLE_RED, Texture.class);
        assetManager.load(QUEEN_DIE_RED, Texture.class);
        assetManager.load(KING_IDLE_RED, Texture.class);
        assetManager.load(KING_WALK_RED, Texture.class);
        assetManager.load(KING_DIE_RED, Texture.class);
        assetManager.load(CLOUD_1, Texture.class);
        assetManager.load(CLOUD_2, Texture.class);
        assetManager.load(CLOUD_3, Texture.class);
        assetManager.load(CLOUD_4, Texture.class);
        assetManager.load(CLOUD_5, Texture.class);
        assetManager.load(CLOUD_6, Texture.class);
        assetManager.load(BLOOD_1, Texture.class);
        assetManager.load(BLOOD_2, Texture.class);
        assetManager.load(BLOOD_3, Texture.class);
        assetManager.load(GRASS_1, Texture.class);
        assetManager.load(GRASS_2, Texture.class);
        assetManager.load(GRASS_3, Texture.class);
        assetManager.load(GRASS_4, Texture.class);
        assetManager.load(TILE, Texture.class);
        assetManager.load(TILE_SELECTED, Texture.class);
        assetManager.load(TILE_MASK, Texture.class);
        assetManager.load(TILE_ACTIVE, Texture.class);
        assetManager.load(TILE_TAKING, Texture.class);
        assetManager.load(CURSOR, Texture.class);
        assetManager.load(CURSOR_MOVE, Texture.class);
        assetManager.load(CURSOR_ATTACK, Texture.class);
        assetManager.load(EXPLOSION, Texture.class);
        assetManager.load(GRASS_BACKGROUND, Texture.class);
        assetManager.load(LOGO, Texture.class);
        assetManager.load(PORTRAIT_1, Texture.class);
        assetManager.load(PORTRAIT_2, Texture.class);
        assetManager.load(PORTRAIT_3, Texture.class);
        assetManager.load(PORTRAIT_4, Texture.class);
        assetManager.load(SPEECH_BUBBLE, Texture.class);

        assetManager.load(MUSIC_FIGHT_0, Sound.class);
        assetManager.load(MUSIC_FIGHT_1, Sound.class);
        assetManager.load(MUSIC_FIGHT_2, Sound.class);
        assetManager.load(MUSIC_FIGHT_3, Sound.class);
        assetManager.load(MUSIC_FAIL, Sound.class);
        assetManager.load(MUSIC_WIN, Sound.class);

        assetManager.load(SOUND_CLANG_1, Sound.class);
        assetManager.load(SOUND_CLANG_2, Sound.class);
        assetManager.load(SOUND_CLANG_3, Sound.class);
        assetManager.load(SOUND_SCREAM_1, Sound.class);
        assetManager.load(SOUND_SCREAM_2, Sound.class);
        assetManager.load(SOUND_SCREAM_3, Sound.class);
        assetManager.load(SOUND_SCREAM_4, Sound.class);
        assetManager.load(SOUND_SCREAM_5, Sound.class);
        assetManager.load(SOUND_ORDER_1, Sound.class);
        assetManager.load(SOUND_ORDER_2, Sound.class);
        assetManager.load(SOUND_ATTACK_1, Sound.class);
        assetManager.load(SOUND_ATTACK_2, Sound.class);
        assetManager.load(SOUND_ATTACK_3, Sound.class);

        assetManager.finishLoading();
        processAnimation(PAWN_IDLE, 4, 0.2f);
        processAnimation(PAWN_WALK, 4, 0.2f);
        processAnimation(PAWN_DIE, 4, 0.1f);
        processAnimation(CASTLE_IDLE, 4, 0.2f);
        processAnimation(CASTLE_WALK, 4, 0.2f);
        processAnimation(CASTLE_DIE, 4, 0.1f);
        processAnimation(KNIGHT_IDLE, 4, 0.2f);
        processAnimation(KNIGHT_WALK, 4, 0.2f);
        processAnimation(KNIGHT_DIE, 4, 0.1f);
        processAnimation(BISHOP_IDLE, 4, 0.2f);
        processAnimation(BISHOP_WALK, 4, 0.2f);
        processAnimation(BISHOP_DIE, 4, 0.1f);
        processAnimation(QUEEN_IDLE, 1, 1f);
        processAnimation(QUEEN_DIE, 4, 0.1f);
        processAnimation(KING_IDLE, 4, 0.2f);
        processAnimation(KING_WALK, 4, 0.2f);
        processAnimation(KING_DIE, 6, 0.3f);
        processAnimation(PAWN_IDLE_RED, 4, 0.2f);
        processAnimation(PAWN_WALK_RED, 4, 0.2f);
        processAnimation(PAWN_DIE_RED, 4, 0.1f);
        processAnimation(CASTLE_IDLE_RED, 4, 0.2f);
        processAnimation(CASTLE_WALK_RED, 4, 0.2f);
        processAnimation(CASTLE_DIE_RED, 4, 0.1f);
        processAnimation(KNIGHT_IDLE_RED, 4, 0.2f);
        processAnimation(KNIGHT_WALK_RED, 4, 0.2f);
        processAnimation(KNIGHT_DIE_RED, 4, 0.1f);
        processAnimation(BISHOP_IDLE_RED, 4, 0.2f);
        processAnimation(BISHOP_WALK_RED, 4, 0.2f);
        processAnimation(BISHOP_DIE_RED, 4, 0.1f);
        processAnimation(QUEEN_IDLE_RED, 1, 1f);
        processAnimation(QUEEN_DIE_RED, 4, 0.1f);
        processAnimation(KING_IDLE_RED, 4, 0.2f);
        processAnimation(KING_WALK_RED, 4, 0.2f);
        processAnimation(KING_DIE_RED, 6, 0.2f);
        processAnimation(BLOOD_1, 6, 0.1f);
        processAnimation(BLOOD_2, 6, 0.1f);
        processAnimation(BLOOD_3, 6, 0.2f);
        processAnimation(CLOUD_1, 1, 1f);
        processAnimation(CLOUD_2, 1, 1f);
        processAnimation(CLOUD_3, 1, 1f);
        processAnimation(CLOUD_4, 1, 1f);
        processAnimation(CLOUD_5, 1, 1f);
        processAnimation(CLOUD_6, 1, 1f);
        processAnimation(GRASS_1, 4, 0.4f);
        processAnimation(GRASS_2, 4, 0.4f);
        processAnimation(GRASS_3, 4, 0.4f);
        processAnimation(GRASS_4, 4, 0.4f);
        processAnimation(TILE, 1, 1f);
        processAnimation(TILE_SELECTED, 2, 0.6f);
        processAnimation(TILE_MASK, 1, 1f);
        processAnimation(TILE_ACTIVE, 2, 0.4f);
        processAnimation(TILE_TAKING, 1, 1f);
        processAnimation(CURSOR, 1, 1f);
        processAnimation(CURSOR_MOVE, 4, 0.1f);
        processAnimation(CURSOR_ATTACK, 4, 0.1f);
        processAnimation(EXPLOSION, 8, 0.08f);
        processAnimation(GRASS_BACKGROUND, 1, 1f);
        processAnimation(LOGO, 2, 0.8f);
        processAnimation(PORTRAIT_1, 1, 1f);
        processAnimation(PORTRAIT_2, 1, 1f);
        processAnimation(PORTRAIT_3, 1, 1f);
        processAnimation(PORTRAIT_4, 1, 1f);
        processAnimation(SPEECH_BUBBLE, 1, 1f);
    }

    public Animation<TextureRegion> getAnimation(String name) {
        return anims.get(name);
    }

    public Sound getSound(String name) {
        return assetManager.get(name, Sound.class);
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
