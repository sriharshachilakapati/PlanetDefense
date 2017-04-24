package com.shc.ld38.states;

import com.shc.ld38.Resources;
import com.shc.ld38.Util;
import com.shc.ld38.entities.Asteroid;
import com.shc.ld38.entities.Attacker;
import com.shc.ld38.entities.Planet;
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
import com.shc.silenceengine.utils.GameTimer;
import com.shc.silenceengine.utils.TaskManager;
import com.shc.silenceengine.utils.TimeUtils;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayState extends GameState
{
    private static final float WIDTH  = 1280;
    private static final float HEIGHT = 720;

    private static OrthoCam camera;
    private static OrthoCam hudCam;
    public static  Scene    scene;

    public static float money;

    private Vector2 previousMouse = new Vector2();

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
        scene.addEntity(new Attacker(Attacker.Type.PLANE));

        CollisionSystem2D collider = new CollisionSystem2D(new Grid((int) WIDTH, (int) HEIGHT, 128, 128));
        collider.register(Attacker.COLLISION_TAG, Projectile.COLLISION_TAG);

        scene.registerUpdateSystem(collider);

        GameTimer ufoSpawn = new GameTimer(3, TimeUtils.Unit.SECONDS);
        ufoSpawn.setCallback(() -> TaskManager.runOnRender(() -> scene.addEntity(new Attacker(Attacker.Type.UFO))));
        ufoSpawn.start();

        GameTimer alienSpawn = new GameTimer(6, TimeUtils.Unit.SECONDS);
        alienSpawn.setCallback(() -> TaskManager.runOnRender(() -> scene.addEntity(new Attacker(Attacker.Type.ALIEN))));
        alienSpawn.start();

        scene.registerRenderSystem(new SceneRenderSystem());

        camera = new OrthoCam();
        hudCam = new OrthoCam();
        resized();

        GLContext.clearColor(new Color(10 / 255f, 42 / 255f, 49 / 255f));
    }

    @Override
    public void update(float delta)
    {
        scene.update(delta);
        Util.update();

        money += 10 * delta;

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
