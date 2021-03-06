package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.lovely.game.Constants.GAME_MENU;
import static com.lovely.game.LoadingManager.CURSOR;
import static com.lovely.game.LoadingManager.CURSOR_ATTACK;
import static com.lovely.game.LoadingManager.CURSOR_MOVE;

public class InputManager {

    final Vector2 mousePos = new Vector2();
    final Vector2 mousePosOnBoard = new Vector2();
    private final Vector3 mouseInWorld3D = new Vector3();
    private boolean clickLock = false;
    boolean justClicked = false;
    CursorState cursorState;

    void start() {
        cursorState = CursorState.SELECT;
        justClicked = false;
        clickLock = false;
    }

    void update(ChessBrawler context) {
        mouseInWorld3D.x = Gdx.input.getX();
        mouseInWorld3D.y = Gdx.input.getY();
        mouseInWorld3D.z = 0;
        context.cameraManager.camera.unproject(mouseInWorld3D);
        mousePos.x = mouseInWorld3D.x;
        mousePos.y = mouseInWorld3D.y;
        mousePosOnBoard.x = mousePos.y;
        mousePosOnBoard.y = mousePos.x;
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            context.changeScreen(GAME_MENU);
        }
        justClicked = false;
        if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (!clickLock) {
                clickLock = true;
                justClicked = true;
            }
        } else {
            clickLock = false;
        }
        if(Gdx.input.isButtonPressed(Input.Buttons.RIGHT)) {
            context.pieceManager.deselect();
        }
        cursorState = CursorState.SELECT;
    }

    enum CursorState {
        SELECT(CURSOR),
        MOVE(CURSOR_MOVE),
        ATTACK(CURSOR_ATTACK);

        String image;

        CursorState(String image) {
            this.image = image;
        }
    }
}
