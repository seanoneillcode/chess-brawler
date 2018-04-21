package com.lovely.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

import static com.lovely.game.Constants.*;

public class CameraManager {

    OrthographicCamera camera;
    private Vector2 cameraPos;

    public CameraManager() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        cameraPos = new Vector2(56, 74);
        camera.position.set(cameraPos, 0);
    }

    void update(ChessBrawler context) {
        camera.position.set(getCameraPosition(cameraPos, context.screenShaker.getShake()));
        camera.update();
    }

    public Vector3 getCameraPosition(Vector2 targetPos, Vector2 screenShake) {
        Vector3 target = new Vector3(targetPos.x, targetPos.y, 0);
        final float speed = CAMERA_CATCHUP_SPEED * Gdx.graphics.getDeltaTime();
        float ispeed = 1.0f - speed;
        Vector3 cameraPosition = camera.position.cpy();
        cameraPosition.scl(ispeed);
        target.scl(speed);
        cameraPosition.add(target);
        float cameraTrailLimit = 100.0f;
        cameraPosition.x = MathUtils.clamp(cameraPosition.x, -cameraTrailLimit + targetPos.x, cameraTrailLimit + targetPos.x);
        cameraPosition.y = MathUtils.clamp(cameraPosition.y, -cameraTrailLimit + targetPos.y, cameraTrailLimit + targetPos.y);
        cameraPosition.x = cameraPosition.x + screenShake.x;
        cameraPosition.y = cameraPosition.y + screenShake.y;
        return cameraPosition;
    }
}
