package com.lovely.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.*;

import static com.lovely.game.LoadingManager.*;

public class SoundManager {

    Map<String, Sound> sounds;
    float soundEffectVolume;
    float musicVolume;
    Sound currentMusic;
    List<String> clangs = Arrays.asList(SOUND_CLANG_1, SOUND_CLANG_2, SOUND_CLANG_3);
    List<String> attacks = Arrays.asList(SOUND_ATTACK_1, SOUND_ATTACK_2, SOUND_ATTACK_3);
    List<String> screams = Arrays.asList(SOUND_SCREAM_1, SOUND_SCREAM_2, SOUND_SCREAM_3, SOUND_SCREAM_4, SOUND_SCREAM_5);

    SoundManager() {
        this.sounds = new HashMap<>();
        this.soundEffectVolume = 0f; // 0.6f
        this.musicVolume = 0f; // 0.4f
    }

    public void playSound(String name, ChessBrawler context) {
        if (!sounds.containsKey(name)) {
            Sound sound = context.loadingManager.getSound(name);
            sounds.put(name, sound);
        }
        float pitch = MathUtils.random(0.8f, 1.0f);
        Sound sound = sounds.get(name);
        sound.play(soundEffectVolume, pitch, 0.5f);
    }

    public void playMusic(String name, ChessBrawler context, boolean loop) {
        if (currentMusic != null) {
            currentMusic.stop();
        }
        if (!sounds.containsKey(name)) {
            Sound sound = context.loadingManager.getSound(name);
            sounds.put(name, sound);
        }
        Sound sound = sounds.get(name);
        currentMusic = sound;
        if (loop) {
            sound.loop(musicVolume);
        } else {
            sound.play(musicVolume);
        }

    }

    public void playClang(ChessBrawler chessBrawler) {
        playSound(clangs.get(MathUtils.random(0, clangs.size() - 1)), chessBrawler);
    }

    public void playAttack(ChessBrawler chessBrawler) {
        playSound(attacks.get(MathUtils.random(0, clangs.size() - 1)), chessBrawler);
    }

    public void playScream(ChessBrawler chessBrawler) {
        playSound(screams.get(MathUtils.random(0, clangs.size() - 1)), chessBrawler);
    }
}
