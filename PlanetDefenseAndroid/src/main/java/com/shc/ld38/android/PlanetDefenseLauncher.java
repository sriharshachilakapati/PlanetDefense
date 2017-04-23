package com.shc.ld38.android;

import com.shc.ld38.PlanetDefense;
import com.shc.silenceengine.backend.android.AndroidRuntime;
import com.shc.silenceengine.backend.android.AndroidLauncher;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlanetDefenseLauncher extends AndroidLauncher
{
    @Override
    public void launchGame()
    {
        AndroidRuntime.start(new PlanetDefense());
    }
}