package com.lovely.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import static com.lovely.game.LoadingManager.TILE;

public class ChessBrawler extends ApplicationAdapter {
    private static final float TILE_SIZE = 16f;
    private static final float HALF_TILE_SIZE = TILE_SIZE / 2.0f;

    SpriteBatch batch;
	LoadingManager loadingManager;
	CameraManager cameraManager;
	InputManager inputManager;
    private float timer = 0;

    @Override
	public void create () {
		loadingManager = new LoadingManager();
        inputManager = new InputManager();
        cameraManager = new CameraManager();
		batch = new SpriteBatch();
		loadingManager.load();
	}

	@Override
	public void render () {
        inputManager.update(this);
        cameraManager.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.setProjectionMatrix(cameraManager.camera.combined);
        drawLevel();
		batch.end();
	}

	private void drawLevel() {
	    for (int x = 0; x < 8; x++) {
	        for (int y = 0; y < 8; y++) {
	            batch.draw(loadingManager.getAnimation(TILE).getKeyFrame(timer), (x * TILE_SIZE) - HALF_TILE_SIZE, (y *  TILE_SIZE) - HALF_TILE_SIZE);
            }
        }
    }

	@Override
	public void dispose () {
		batch.dispose();
		loadingManager.dispose();
	}
}
