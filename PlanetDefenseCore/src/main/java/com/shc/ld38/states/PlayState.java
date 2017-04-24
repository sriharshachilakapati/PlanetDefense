package com.shc.ld38.states;

import com.shc.ld38.Resources;
import com.shc.ld38.Util;
import com.shc.ld38.entities.Asteroid;
import com.shc.ld38.entities.Attacker;
import com.shc.ld38.entities.Planet;
import com.shc.ld38.entities.PlayButton;
import com.shc.ld38.entities.Projectile;
import com.shc.silenceengine.collision.broadphase.Grid;
import com.shc.silenceengine.collision.colliders.CollisionSystem2D;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.IGraphicsDevice;
import com.shc.silenceengine.graphics.SceneRenderSystem;
import com.shc.silenceengine.graphics.cameras.OrthoCam;
import com.shc.silenceengine.graphics.fonts.BitmapFontRenderer;
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
    public static final float WIDTH  = 1280;
    public static final float HEIGHT = 720;

    private static OrthoCam camera;
    private static OrthoCam hudCam;

    public static Scene scene;
    public static float money;

    private static int waveNo = 0;

    private Scene hud;

    private Vector2 previousMouse = new Vector2();

    public static void generateNewWave()
    {
        waveNo++;

        float y = -207;

        for (int j = 0; j < waveNo % 3; j++)
        {
            Attacker.Type type = j == 0 ? Attacker.Type.PLANE : j == 1 ? Attacker.Type.UFO : Attacker.Type.ALIEN;

            for (int i = 0; i < waveNo; i++)
                scene.addEntity(new Attacker(type, 1103, y -= 100));
        }
    }

    @Override
    public void onEnter()
    {
        money = 1000;
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

        CollisionSystem2D collider = new CollisionSystem2D(new Grid((int) WIDTH, (int) HEIGHT, 128, 128));

        collider.register(Attacker.COLLISION_TAG, Projectile.COLLISION_TAG);
        collider.register(Asteroid.COLLISION_TAG, Projectile.COLLISION_TAG);
        collider.register(Planet.COLLISION_TAG, Attacker.COLLISION_TAG);

        scene.registerUpdateSystem(collider);

        scene.registerRenderSystem(new SceneRenderSystem());

        hud = new Scene();
        hud.addEntity(new PlayButton());
        hud.registerRenderSystem(new SceneRenderSystem());

        PlayButton.enabled = true;

        camera = new OrthoCam();
        hudCam = new OrthoCam();
        resized();

        GLContext.clearColor(new Color(10 / 255f, 42 / 255f, 49 / 255f));
    }

    @Override
    public void update(float delta)
    {
        hudCam.apply();
        Util.update();
        hud.update(delta);

        camera.apply();
        Util.update();
        scene.update(delta);

        money += 10 * delta;

        PlayButton.enabled = Attacker.Statistics.instances == 0;

        if (Mouse.isButtonTapped(Mouse.BUTTON_RIGHT))
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

        hudCam.apply();
        hud.render(delta);

        BitmapFontRenderer fontRenderer = IGraphicsDevice.Renderers.bitmapFont;
        IGraphicsDevice.Programs.font.use();

        fontRenderer.begin();
        {
            fontRenderer.render(Resources.Fonts.DEFAULT, "Money: " + (int) money, 10, 10);
        }
        fontRenderer.end();
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
        hudCam.initProjection(WIDTH, HEIGHT);
        GLContext.viewport(0, 0, displayWidth, displayHeight);
    }
}
