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
    LevelManager levelManager;
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
        levelManager = new LevelManager();
        pieceOffset = new Vector2(4, 0);
        aiPlayer = new AiPlayer(aiOwner, 2);
		batch = new SpriteBatch();
		loadingManager.load();
		isTesting = false;
		startGame();
		screen = PLAYING_GAME;
        Gdx.input.setCursorCatched(true);
	}

	void changeScreen(String screen) {
        waitingForContinue = true;
        if (screen.equals(GAME_WON)) {
            String gameLoser = gameWinner.equals(RED) ? BLUE : RED;
            effectsManager.blowUpPlayer(gameLoser, this);
            String winLose = gameWinner.equals(playerOwner) ? MUSIC_WIN : MUSIC_FAIL;
            soundManager.playMusic(winLose, this, false);
            cameraManager.targetZoom = 0.4f;
            Vector2 pos = pieceManager.getKingPiece(gameLoser).pos.cpy();
            cameraManager.cameraPos = new Vector2(pos.y, pos.x);
        }

        this.screen = screen;

    }

	void startGame() {
        effectsManager.start();
        waitingForContinue = false;
        inputManager.start();
        pieceManager.start();
        levelManager.start();
        gameWinner = null;
		boardManager.createPieces(this);
		screenTimer = 1f;
		List<String> fightSongs = Arrays.asList(MUSIC_FIGHT_0, MUSIC_FIGHT_1, MUSIC_FIGHT_2, MUSIC_FIGHT_3);
        soundManager.playMusic(fightSongs.get(MathUtils.random(fightSongs.size() - 1)), this, true);
    }

	@Override
	public void render () {
        effectsManager.update();
        inputManager.update(this);
        if (screen.equals(PLAYING_GAME)) {
            pieceManager.update(this);
        }
        levelManager.update();
        aiPlayer.update(this);
        screenShaker.update();
        cameraManager.update(this);
        update();
		Gdx.gl.glClearColor(0f / 255f, 149f / 255f, 233f / 255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
        batch.setProjectionMatrix(cameraManager.camera.combined);
        drawBackground();
        drawGround();
        drawBoard();
        drawEffects();
        drawSelectionTiles();
        drawPieces();
        drawText();
        drawCursor();
		batch.end();
	}

	void update() {
        if (screen.equals(GAME_WON)) {
            if (waitingForContinue && inputManager.justClicked) {
                screen = PLAYING_GAME;
                cameraManager.targetZoom = 1f;
                cameraManager.cameraPos = new Vector2(56, 74);
                startGame();
            }
        }
    }

    private void drawCursor() {
        batch.draw(loadingManager.getAnimation(inputManager.cursorState.image).getKeyFrame(timer, true), inputManager.mousePos.x - 8, inputManager.mousePos.y - 8);
    }

    private void drawText() {
        if (screen.equals(GAME_WON)) {
            String msg = playerOwner.equals(gameWinner) ? "YOU WIN" : "YOU LOSE";

            textManager.drawText(batch, msg, cameraManager.cameraPos.cpy().add(-24, 16), Color.YELLOW);
            textManager.drawText(batch, "RETRY",cameraManager.cameraPos.cpy().add(-16, -16));
        }
        if (screen.equals(PLAYING_GAME)) {
            if (waitingForContinue) {
                textManager.drawText(batch, "FIGHT!", new Vector2(40, 104));
            }
        }
    }

    void drawBackground() {
        for (Drawable drawable : levelManager.clouds) {
            batch.draw(loadingManager.getAnimation(drawable.image).getKeyFrame(drawable.animTimer, true), drawable.pos.x, drawable.pos.y);
        }
        batch.draw(loadingManager.getAnimation(GRASS_BACKGROUND).getKeyFrame(0), -84, -36);
    }

    private void drawEffects() {
        timer += Gdx.graphics.getDeltaTime();
        Sprite sprite = new Sprite();
        for (Effect effect : effectsManager.effects) {
            TextureRegion region = loadingManager.getAnimation(effect.image).getKeyFrame(effect.animTimer, false);
            sprite.setSize(region.getRegionWidth(), region.getRegionHeight());
            sprite.setPosition(effect.pos.y - (region.getRegionWidth() / 2.0f) + pieceOffset.y,
                    effect.pos.x - (region.getRegionHeight() / 2.0f) + pieceOffset.x);
            sprite.setRegion(region);
            sprite.draw(batch);
        }
    }

    private void drawPieces() {
        Sprite sprite = new Sprite();
        for (Piece piece : pieceManager.pieces) {
            boolean loop = true;
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
        for (Drawable drawable : levelManager.shadowsClouds) {
            batch.draw(loadingManager.getAnimation(drawable.image).getKeyFrame(drawable.animTimer, true), drawable.pos.x, drawable.pos.y);
        }
    }

    private void drawGround() {
        for (Drawable drawable : levelManager.grass) {
            batch.draw(loadingManager.getAnimation(drawable.image).getKeyFrame(drawable.animTimer, true), drawable.pos.x, drawable.pos.y);
        }
    }

    private void drawBoard() {
        boolean isOtherTile = false;
        for (int x = 0; x < 8; x++) {
            isOtherTile = !isOtherTile;
            for (int y = 0; y < 8; y++) {
                if (isOtherTile) {
                    batch.draw(loadingManager.getAnimation(TILE_MASK).getKeyFrame(0), (y * TILE_SIZE) - HALF_TILE_SIZE, (x * TILE_SIZE) - HALF_TILE_SIZE);
                }
                isOtherTile = !isOtherTile;
            }
        }
    }

	private void drawSelectionTiles() {
	    for (int x = 0; x < 8; x++) {
	        for (int y = 0; y < 8; y++) {
	            TextureRegion region = null;
	            Move move = pieceManager.getLegalMove(x, y);
	            if (move != null) {
	                if (move.taking) {
                        region = loadingManager.getAnimation(TILE_TAKING).getKeyFrame(timer, true);
                    } else {
                        region = loadingManager.getAnimation(TILE_ACTIVE).getKeyFrame(timer, true);
                    }
                }
                if (region != null) {
                    batch.draw(region, (y * TILE_SIZE) - HALF_TILE_SIZE, (x * TILE_SIZE) - HALF_TILE_SIZE);
                }
                if (pieceManager.selectedPiece != null) {
                    batch.draw(loadingManager.getAnimation(TILE_SELECTED).getKeyFrame(timer, true), pieceManager.selectedPiece.pos.y - HALF_TILE_SIZE, pieceManager.selectedPiece.pos.x - HALF_TILE_SIZE);
                }
            }
        }
    }

	@Override
	public void dispose () {
		batch.dispose();
		loadingManager.dispose();
	}
}
