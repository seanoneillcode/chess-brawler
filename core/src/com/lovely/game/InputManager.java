package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class InputManager {

    final Vector2 mousePos = new Vector2();
    final Vector2 mousePosOnBoard = new Vector2();
    private final Vector3 mouseInWorld3D = new Vector3();
    private boolean clickLock = false;
    boolean justClicked = false;


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
    }
}
