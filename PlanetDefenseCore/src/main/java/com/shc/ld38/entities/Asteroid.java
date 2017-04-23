package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.silenceengine.collision.CollisionTag;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.scene.Component;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.components.BoundsRenderComponent2D;
import com.shc.silenceengine.scene.components.CollisionComponent2D;
import com.shc.silenceengine.scene.components.PolygonRenderComponent;
import com.shc.silenceengine.scene.components.SpriteComponent;

/**
 * @author Sri Harsha Chilakapati
 */
public class Asteroid extends Entity
{
    private static final CollisionTag COLLISION_TAG = new CollisionTag();

    public Asteroid(float x, float y)
    {
        addComponent(new SpriteComponent(new Sprite(Resources.Textures.ASTEROID)));
        addComponent(new CollisionComponent2D(COLLISION_TAG, Resources.Polygons.ASTEROID.copy()));

        if (Game.DEVELOPMENT)
        {
            addComponent(new BoundsRenderComponent2D(Color.RED));
            addComponent(new PolygonRenderComponent(Color.GREEN));
        }

        addComponent(new Behaviour());
        transformComponent.setPosition(x, y);
    }

    private static class Behaviour extends Component
    {
        @Override
        protected void onUpdate(float elapsedTime)
        {
            transformComponent.rotate(35 * elapsedTime);
        }
    }
}
