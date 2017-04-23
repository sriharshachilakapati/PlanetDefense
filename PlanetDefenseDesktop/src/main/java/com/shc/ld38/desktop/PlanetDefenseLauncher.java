package com.shc.ld38.desktop;

import com.shc.silenceengine.backend.lwjgl.LwjglRuntime;
import com.shc.ld38.PlanetDefense;

public class PlanetDefenseLauncher
{
    public static void main(String[] args)
    {
        LwjglRuntime.start(new PlanetDefense());
    }
}