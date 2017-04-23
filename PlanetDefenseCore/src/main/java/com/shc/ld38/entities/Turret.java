package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.components.SpriteComponent;

/**
 * @author Sri Harsha Chilakapati
 */
public class Turret extends Entity
{
    public Turret()
    {
        SpriteComponent component = new SpriteComponent(new Sprite(Resources.Textures.TURRET_BLUE));
        component.layer = -1;

        addComponent(component);

        transformComponent.setPosition(0, 0);
    }
}
