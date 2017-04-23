package com.shc.ld38.states;

import com.shc.ld38.Util;
import com.shc.ld38.entities.Asteroid;
import com.shc.ld38.entities.Planet;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.SceneRenderSystem;
import com.shc.silenceengine.graphics.cameras.OrthoCam;
import com.shc.silenceengine.graphics.opengl.GLContext;
import com.shc.silenceengine.input.Mouse;
import com.shc.silenceengine.scene.Scene;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayState extends GameState
{
    private OrthoCam camera;
    private Scene    scene;

    @Override
    public void onEnter()
    {
        scene = new Scene();

        scene.addEntity(new Planet());
        scene.addEntity(new Asteroid(160, 152));
        scene.addEntity(new Asteroid(515, 100));
        scene.addEntity(new Asteroid(1119, 107));
        scene.addEntity(new Asteroid(981, 589));
        scene.addEntity(new Asteroid(397, 639));
        scene.addEntity(new Asteroid(160, 483));
        scene.addEntity(new Asteroid(426, 405));

        scene.registerRenderSystem(new SceneRenderSystem());

        camera = new OrthoCam(SilenceEngine.display.getWidth(), SilenceEngine.display.getHeight());
        resized();
    }

    @Override
    public void update(float delta)
    {
        scene.update(delta);
        Util.update();

        if (Mouse.isButtonTapped(Mouse.BUTTON_LEFT))
            SilenceEngine.log.getRootLogger().info(Util.getMouseInView());

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
        camera.initProjection(SilenceEngine.display.getWidth(), SilenceEngine.display.getHeight());
        GLContext.viewport(0, 0, SilenceEngine.display.getWidth(), SilenceEngine.display.getHeight());
    }
}
