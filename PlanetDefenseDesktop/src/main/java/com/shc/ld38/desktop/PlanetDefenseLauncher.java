package com.shc.ld38.desktop;

import com.shc.ld38.PlanetDefense;
import com.shc.silenceengine.backend.lwjgl.LwjglRuntime;
import com.shc.silenceengine.core.SilenceEngine;
import org.lwjgl.util.tinyfd.TinyFileDialogs;

public class PlanetDefenseLauncher
{
    public static void main(String[] args)
    {
        SilenceEngine.runOnInit((next) ->
        {
            TinyFileDialogs.tinyfd_version.charAt(0);
            next.invoke();
        });

        LwjglRuntime.start(new PlanetDefense());
    }
}