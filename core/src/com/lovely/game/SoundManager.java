package com.lovely.game;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

import java.util.HashMap;
import java.util.Map;

public class SoundManager {

    Map<String, Sound> sounds;
    float soundEffectVolume;
    float musicVolume;
    Sound currentMusic;

    SoundManager() {
        this.sounds = new HashMap<>();
        this.soundEffectVolume = 1.0f;
        this.musicVolume = 0f; // 0.2f
    }

    public void playSound(String name, ChessBrawler context) {
        if (!sounds.containsKey(name)) {
            Sound sound = context.loadingManager.getSound(name);
            sounds.put(name, sound);
        }
        float pitch = MathUtils.random(0.8f, 1.2f);
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
}
