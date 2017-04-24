package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.silenceengine.collision.CollisionTag;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.input.Keyboard;
import com.shc.silenceengine.scene.Component;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.components.BoundsRenderComponent2D;
import com.shc.silenceengine.scene.components.CollisionComponent2D;
import com.shc.silenceengine.scene.components.PolygonRenderComponent;
import com.shc.silenceengine.scene.components.SpriteComponent;

/**
 * @author Sri Harsha Chilakapati
 */
public class Planet extends Entity
{
    public static final CollisionTag COLLISION_TAG = new CollisionTag();

    public Planet()
    {
        addComponent(new SpriteComponent(new Sprite(Resources.Textures.PLANET)));
        addComponent(new CollisionComponent2D(COLLISION_TAG, Resources.Polygons.PLANET.copy(), this::onCollision));

        if (Game.DEVELOPMENT)
        {
            addComponent(new PolygonRenderComponent(Color.GREEN));
            addComponent(new BoundsRenderComponent2D(Color.RED));
        }

        addComponent(new Behaviour());

        transformComponent.setPosition(400, 720);
    }

    private void onCollision(CollisionComponent2D other)
    {
        if (other.tag == Attacker.COLLISION_TAG)
            other.getEntity().destroy();
    }

    private static class Behaviour extends Component
    {
        @Override
        protected void onUpdate(float elapsedTime)
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_A))
                transformComponent.rotate(-45 * elapsedTime);

            if (Keyboard.isKeyDown(Keyboard.KEY_D))
                transformComponent.rotate(45 * elapsedTime);

            transformComponent.rotate(5 * elapsedTime);
        }
    }
}
