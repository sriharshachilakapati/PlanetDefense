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
import com.shc.silenceengine.scene.Scene;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayState extends GameState
{
    public static OrthoCam camera;
    public static Scene    scene;

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

        camera = new OrthoCam(SilenceEngine.display.getWidth(), SilenceEngine.display.getHeight());
        resized();

        GLContext.clearColor(new Color(10/255f, 42/255f, 49/255f));
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
