package com.shc.ld38;

import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.input.Keyboard;
import com.shc.silenceengine.io.FilePath;

public class PlanetDefense extends Game
{
    public static Game INSTANCE;

    @Override
    public void init()
    {
        if (SilenceEngine.display.getPlatform() != SilenceEngine.Platform.ANDROID)
            SilenceEngine.input.setSimulateTouch(true);

        INSTANCE = this;

        SilenceEngine.display.setTitle("PlanetDefense: SilenceEngine " + SilenceEngine.getVersionString());
        SilenceEngine.display.setIcon(FilePath.getResourceFile("textures/icon.png"));
        SilenceEngine.display.setSize(1280, 720);
        SilenceEngine.display.centerOnScreen();

        Resources.load();
    }

    @Override
    public void update(float deltaTime)
    {
        if (Keyboard.isKeyTapped(Keyboard.KEY_ESCAPE))
            SilenceEngine.display.close();
    }
}