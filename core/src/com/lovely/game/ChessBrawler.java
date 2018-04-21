package com.lovely.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.Arrays;
import java.util.List;

import static com.lovely.game.Constants.*;
import static com.lovely.game.LoadingManager.*;

public class ChessBrawler extends ApplicationAdapter {

    SpriteBatch batch;
	LoadingManager loadingManager;
	CameraManager cameraManager;
	InputManager inputManager;
	PieceManager pieceManager;
	BoardManager boardManager;
	ScreenShaker screenShaker;
	TextManager textManager;
	AiPlayer aiPlayer;
    EffectsManager effectsManager;
    SoundManager soundManager;
    private float timer = 0;
    public String playerOwner = RED;
    public String aiOwner = BLUE;
    Vector2 pieceOffset;
    public boolean isTesting;
    String gameWinner;
    String screen;
    float screenTimer = 0;
    private boolean waitingForContinue = false;

    @Override
	public void create () {
		loadingManager = new LoadingManager();
        inputManager = new InputManager();
        cameraManager = new CameraManager();
        pieceManager = new PieceManager();
        boardManager = new BoardManager();
        screenShaker = new ScreenShaker();
        textManager = new TextManager();
        effectsManager = new EffectsManager();
        soundManager = new SoundManager();
        pieceOffset = new Vector2(4, 0);
        aiPlayer = new AiPlayer(aiOwner, 2);
		batch = new SpriteBatch();
		loadingManager.load();
		isTesting = false;
		startGame();
		screen = PLAYING_GAME;
	}

	void changeScreen(String screen) {
        waitingForContinue = true;
        if (screen.equals(GAME_WON)) {
            effectsManager.blowUpPlayer(gameWinner.equals(RED) ? BLUE : RED, this);
            String winLose = gameWinner.equals(RED) ? MUSIC_WIN : MUSIC_FAIL;
            soundManager.playMusic(winLose, this, false);
        }
        this.screen = screen;

    }

	void startGame() {
        waitingForContinue = false;
        inputManager.start();
        pieceManager.start();
        gameWinner = null;
		boardManager.createPieces(this);
		screenTimer = 1f;
		List<String> fightSongs = Arrays.asList(MUSIC_FIGHT_0, MUSIC_FIGHT_1, MUSIC_FIGHT_2);
        soundManager.playMusic(fightSongs.get(MathUtils.random(fightSongs.size() - 1)), this, true);
    }

	@Override
	public void render () {
        effectsManager.update();
        inputManager.update(this);
        if (screen.equals(PLAYING_GAME)) {
            pieceManager.update(this);
        }
        aiPlayer.update(this);
        screenShaker.update();
        cameraManager.update(this);
        update();
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.setProjectionMatrix(cameraManager.camera.combined);
        drawLevel();
        drawPieces();
        drawEffects();
        drawText();
		batch.end();
	}

	void update() {
        if (screen.equals(GAME_WON)) {
            if (waitingForContinue && inputManager.justClicked) {
                screen = PLAYING_GAME;
                startGame();
            }
        }
    }


    private void drawText() {
        if (screen.equals(GAME_WON)) {
            String msg = playerOwner.equals(gameWinner) ? "You win!" : "You lose!";
            textManager.drawText(batch, msg, new Vector2(40, 104));
            textManager.drawText(batch, "(click to play again)", new Vector2(12, 68));
        }
        if (screen.equals(PLAYING_GAME)) {
            if (waitingForContinue) {
                textManager.drawText(batch, "FIGHT!", new Vector2(40, 104));
            }
        }
    }

    private void drawEffects() {
        Sprite sprite = new Sprite();
        for (Effect effect : effectsManager.effects) {
            if (effect.offsetTimer < 0) {
                TextureRegion region = loadingManager.getAnimation(effect.image).getKeyFrame(effect.animTimer, true);
                sprite.setSize(region.getRegionWidth(), region.getRegionHeight());
                sprite.setPosition(effect.pos.y - (region.getRegionWidth() / 2.0f) + pieceOffset.y,
                        effect.pos.x - (region.getRegionHeight() / 2.0f) + pieceOffset.x);
                sprite.setRegion(region);
                sprite.draw(batch);
            }
        }
    }

    private void drawPieces() {
        Sprite sprite = new Sprite();
        for (Piece piece : pieceManager.pieces) {
            boolean loop = true;
            sprite.setColor(piece.owner.equals(RED) ? (new Color(1.0f, 1.0f, 1.0f, 1.0f)) : (new Color(0.0f, 1.0f, 1.0f, 1.0f)));
            if (pieceManager.selectedPiece != null && pieceManager.selectedPiece == piece) {
                sprite.setColor(new Color(0.5f, 1.0f, 0.5f, 1f));
            }
            String image = piece.idleImage;
            if (piece.animState == Piece.AnimState.WALK) {
                image = piece.walkImage;
            }
            if (piece.animState == Piece.AnimState.DIE) {
                loop = false;
                image = piece.dieImage;
            }
            TextureRegion region = loadingManager.getAnimation(image).getKeyFrame(piece.animTimer, loop);
            sprite.setSize(region.getRegionWidth(), region.getRegionHeight());
            sprite.setPosition(piece.pos.y - (region.getRegionWidth() / 2.0f) + pieceOffset.y,
                    piece.pos.x - (region.getRegionHeight() / 2.0f) + pieceOffset.x);
            sprite.setRegion(region);
            if (!piece.owner.equals(playerOwner)) {
                sprite.flip(true, false);
            }
            sprite.draw(batch);
        }
    }

	private void drawLevel() {
	    for (int x = 0; x < 8; x++) {
	        for (int y = 0; y < 8; y++) {
	            TextureRegion region;
	            Move move = pieceManager.getLegalMove(x, y);
	            if (move != null) {
	                if (move.taking) {
                        region = loadingManager.getAnimation(TILE_TAKING).getKeyFrame(timer);
                    } else {
                        region = loadingManager.getAnimation(TILE_ACTIVE).getKeyFrame(timer);
                    }
                } else {
                    region = loadingManager.getAnimation(TILE).getKeyFrame(timer);
                }
                batch.draw(region, (y * TILE_SIZE) - HALF_TILE_SIZE, (x *  TILE_SIZE) - HALF_TILE_SIZE);

            }
        }
    }

	@Override
	public void dispose () {
		batch.dispose();
		loadingManager.dispose();
	}
}
