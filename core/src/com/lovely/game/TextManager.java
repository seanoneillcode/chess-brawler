package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class TextManager {

    private BitmapFont font;

    TextManager() {
        loadFonts("bit-16.fnt");
    }

    public void drawText(SpriteBatch batch, String text, Vector2 pos) {
        font.getData().setScale(1.0f);
        font.setColor(Color.BLACK);
        font.draw(batch, text, pos.x, pos.y - 1 );
        font.setColor(Color.WHITE);
        font.draw(batch, text, pos.x, pos.y);
    }

    private BitmapFont loadFonts(String fontString) {
        font = new BitmapFont(Gdx.files.internal(fontString),false);
        font.setUseIntegerPositions(false);
        font.setColor(Color.WHITE);
        return font;
    }
}
