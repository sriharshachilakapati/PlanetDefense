package com.shc.ld38.html;

import com.google.gwt.core.client.EntryPoint;
import com.shc.silenceengine.backend.gwt.GwtRuntime;
import com.shc.ld38.PlanetDefense;

public class PlanetDefenseLauncher implements EntryPoint
{
    @Override
    public void onModuleLoad()
    {
        GwtRuntime.start(new PlanetDefense());
    }
}