package com.shc.ld38.states;

import com.shc.ld38.Resources;
import com.shc.ld38.Util;
import com.shc.ld38.entities.Asteroid;
import com.shc.ld38.entities.Planet;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.DynamicRenderer;
import com.shc.silenceengine.graphics.IGraphicsDevice;
import com.shc.silenceengine.graphics.SceneRenderSystem;
import com.shc.silenceengine.graphics.cameras.OrthoCam;
import com.shc.silenceengine.graphics.opengl.GLContext;
import com.shc.silenceengine.graphics.opengl.Primitive;
import com.shc.silenceengine.graphics.opengl.Texture;
import com.shc.silenceengine.input.Mouse;
import com.shc.silenceengine.scene.Scene;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayState extends GameState
{
    public static OrthoCam camera;
    public static Scene    scene;

    private OrthoCam skyCamera;

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
        skyCamera = new OrthoCam(SilenceEngine.display.getWidth(), SilenceEngine.display.getHeight());
        resized();
    }

    @Override
    public void update(float delta)
    {
        scene.update(delta);
        Util.update();
        Resources.Animations.SKY.update(delta);

        if (Mouse.isButtonTapped(Mouse.BUTTON_LEFT))
            SilenceEngine.log.getRootLogger().info(Util.getMouseInView());

        SilenceEngine.display.setTitle("PlanetDefense | FPS: " + SilenceEngine.gameLoop.getFPS() + " | UPS: " +
                                       SilenceEngine.gameLoop.getUPS());
    }

    @Override
    public void render(float delta)
    {
        skyCamera.apply();
        DynamicRenderer renderer = IGraphicsDevice.Renderers.dynamic;
        IGraphicsDevice.Programs.dynamic.use();

        Texture sky = Resources.Animations.SKY.getCurrentFrame();
        sky.bind();

        renderer.begin(Primitive.TRIANGLE_FAN);
        {
            renderer.vertex(0, 0);
            renderer.texCoord(sky.getMinU(), sky.getMinV());

            renderer.vertex(SilenceEngine.display.getWidth(), 0);
            renderer.texCoord(sky.getMaxU(), sky.getMinV());

            renderer.vertex(SilenceEngine.display.getWidth(), SilenceEngine.display.getHeight());
            renderer.texCoord(sky.getMaxU(), sky.getMaxV());

            renderer.vertex(0, SilenceEngine.display.getHeight());
            renderer.texCoord(sky.getMinU(), sky.getMaxV());
        }
        renderer.end();

        camera.apply();
        scene.render(delta);
    }

    @Override
    public void resized()
    {
        camera.initProjection(SilenceEngine.display.getWidth(), SilenceEngine.display.getHeight());
        skyCamera.initProjection(SilenceEngine.display.getWidth(), SilenceEngine.display.getHeight());
        GLContext.viewport(0, 0, SilenceEngine.display.getWidth(), SilenceEngine.display.getHeight());
    }
}
