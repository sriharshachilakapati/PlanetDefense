package com.shc.ld38.states;

import com.shc.ld38.Util;
import com.shc.ld38.entities.Asteroid;
import com.shc.ld38.entities.Planet;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.SceneRenderSystem;
import com.shc.silenceengine.graphics.cameras.OrthoCam;
import com.shc.silenceengine.graphics.opengl.GLContext;
import com.shc.silenceengine.input.Mouse;
import com.shc.silenceengine.input.Touch;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.scene.Scene;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayState extends GameState
{
    private static final float WIDTH  = 1280;
    private static final float HEIGHT = 720;

    private static OrthoCam camera;
    public static  Scene    scene;

    private Vector2 previousMouse = new Vector2();

    @Override
    public void onEnter()
    {
        scene = new Scene();

        scene.addEntity(new Planet());
        scene.addEntity(new Asteroid(734, 553));
        scene.addEntity(new Asteroid(1079, 546));
        scene.addEntity(new Asteroid(292, 398));
        scene.addEntity(new Asteroid(288, 127));
        scene.addEntity(new Asteroid(486, 243));
        scene.addEntity(new Asteroid(792, 318));
        scene.addEntity(new Asteroid(930, 49));
        scene.addEntity(new Asteroid(1154, 209));

        scene.registerRenderSystem(new SceneRenderSystem());

        camera = new OrthoCam();
        resized();

        GLContext.clearColor(new Color(10 / 255f, 42 / 255f, 49 / 255f));
    }

    @Override
    public void update(float delta)
    {
        scene.update(delta);
        Util.update();

        if (Mouse.isButtonTapped(Mouse.BUTTON_LEFT))
            SilenceEngine.log.getRootLogger().info(Util.getMouseInView());

        if (Touch.isFingerTapped(Touch.FINGER_0))
            previousMouse.set(Touch.getFingerPosition(Touch.FINGER_0));

        if (Touch.isFingerDown(Touch.FINGER_0))
        {
            Vector2 pos = Touch.getFingerPosition(Touch.FINGER_0);

            final float dx = pos.x - previousMouse.x;
            final float dy = pos.y - previousMouse.y;

            previousMouse.set(pos);

            camera.translate(dx, dy);
        }

        SilenceEngine.display.setTitle("PlanetDefense | FPS: " + SilenceEngine.gameLoop.getFPS() + " | UPS: " +
                                       SilenceEngine.gameLoop.getUPS());
    }

    @Override
    public void render(float delta)
    {
        camera.apply();
        scene.render(delta);
    }

    @Override
    public void resized()
    {
        final int displayWidth = SilenceEngine.display.getWidth();
        final int displayHeight = SilenceEngine.display.getHeight();

        final float aspect = (float) displayWidth / (float) displayHeight;

        float projectionWidth;
        float projectionHeight;

        if (displayWidth < displayHeight)
        {
            projectionWidth = WIDTH;
            projectionHeight = WIDTH / aspect;
        }
        else
        {
            projectionWidth = HEIGHT * aspect;
            projectionHeight = HEIGHT;
        }

        camera.initProjection(projectionWidth, projectionHeight);
        GLContext.viewport(0, 0, displayWidth, displayHeight);
    }
}
