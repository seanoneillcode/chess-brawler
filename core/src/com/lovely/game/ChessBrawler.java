package com.lovely.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static com.lovely.game.Constants.HALF_TILE_SIZE;
import static com.lovely.game.Constants.RED;
import static com.lovely.game.Constants.TILE_SIZE;
import static com.lovely.game.LoadingManager.TILE;
import static com.lovely.game.LoadingManager.TILE_ACTIVE;

public class ChessBrawler extends ApplicationAdapter {

    SpriteBatch batch;
	LoadingManager loadingManager;
	CameraManager cameraManager;
	InputManager inputManager;
	PieceManager pieceManager;
	BoardManager boardManager;
    private float timer = 0;
    public String playerOwner = RED;
    Vector2 pieceOffset;
    public boolean isTesting;

    @Override
	public void create () {
		loadingManager = new LoadingManager();
        inputManager = new InputManager();
        cameraManager = new CameraManager();
        pieceManager = new PieceManager();
        boardManager = new BoardManager();
        pieceOffset = new Vector2(0, 4);
		batch = new SpriteBatch();
		loadingManager.load();
		isTesting = true;
		startGame();
	}

	void startGame() {
        pieceManager.pieces.clear();
		boardManager.createPieces(this);
    }

	@Override
	public void render () {
        inputManager.update(this);
        pieceManager.update(this);
        cameraManager.update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.setProjectionMatrix(cameraManager.camera.combined);
        drawLevel();
        drawPieces();
		batch.end();
	}

	private void drawPieces() {
        Sprite sprite = new Sprite();
        for (Piece piece : pieceManager.pieces) {
            sprite.setColor(piece.owner.equals(RED) ? (new Color(1.0f, 1.0f, 1.0f, 1.0f)) : (new Color(0.0f, 1.0f, 1.0f, 1.0f)));
            if (pieceManager.selectedPiece != null && pieceManager.selectedPiece == piece) {
                sprite.setColor(new Color(0.5f, 1.0f, 0.5f, 1f));
            }
            TextureRegion region = loadingManager.getAnimation(piece.image).getKeyFrame(timer, true);
            sprite.setSize(region.getRegionWidth(), region.getRegionHeight());
            sprite.setPosition(piece.pos.x - (region.getRegionWidth() / 2.0f) + pieceOffset.x,
                    piece.pos.y - (region.getRegionHeight() / 2.0f) + pieceOffset.y);
            sprite.setRegion(region);
            sprite.draw(batch);
        }
    }

	private void drawLevel() {
	    for (int x = 0; x < 8; x++) {
	        for (int y = 0; y < 8; y++) {
	            TextureRegion region;
	            if (pieceManager.isLegalMove(x, y)) {
                    region = loadingManager.getAnimation(TILE_ACTIVE).getKeyFrame(timer);
                } else {
                    region = loadingManager.getAnimation(TILE).getKeyFrame(timer);
                }
                batch.draw(region, (x * TILE_SIZE) - HALF_TILE_SIZE, (y *  TILE_SIZE) - HALF_TILE_SIZE);

            }
        }
    }

	@Override
	public void dispose () {
		batch.dispose();
		loadingManager.dispose();
	}
}
