package com.shc.ld38;

import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.cameras.OrthoCam;
import com.shc.silenceengine.graphics.opengl.GLContext;
import com.shc.silenceengine.input.Keyboard;
import com.shc.silenceengine.io.FilePath;

public class PlanetDefense extends Game
{
    public static final float WIDTH  = 1280;
    public static final float HEIGHT = 720;

    public static OrthoCam camera;
    public static OrthoCam hudCam;

    public static Game INSTANCE;

    @Override
    public void init()
    {
        if (SilenceEngine.display.getPlatform() != SilenceEngine.Platform.ANDROID)
            SilenceEngine.input.setSimulateTouch(true);

        DEVELOPMENT = false;

        INSTANCE = this;

        SilenceEngine.display.setTitle("PlanetDefense: SilenceEngine " + SilenceEngine.getVersionString());
        SilenceEngine.display.setIcon(FilePath.getResourceFile("textures/icon.png"));
        SilenceEngine.display.setSize(1280, 720);
        SilenceEngine.display.centerOnScreen();

        GLContext.clearColor(new Color(5 / 255f, 37 / 255f, 44 / 255f));

        Resources.load();

        camera = new OrthoCam();
        hudCam = new OrthoCam();
        resized();
    }

    @Override
    public void update(float deltaTime)
    {
        if (Keyboard.isKeyTapped(Keyboard.KEY_ESCAPE))
            SilenceEngine.display.close();
    }

    @Override
    public void resized()
    {
        if (camera == null || hudCam == null)
            return;

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