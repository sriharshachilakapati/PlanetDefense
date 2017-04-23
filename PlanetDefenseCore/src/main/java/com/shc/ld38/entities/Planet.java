package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.ld38.Util;
import com.shc.silenceengine.collision.CollisionTag;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.graphics.cameras.Camera;
import com.shc.silenceengine.graphics.cameras.OrthoCam;
import com.shc.silenceengine.input.Keyboard;
import com.shc.silenceengine.input.Touch;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.Vector3;
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
        addComponent(new CollisionComponent2D(COLLISION_TAG, Resources.Polygons.PLANET.copy()));

        if (Game.DEVELOPMENT)
        {
            addComponent(new PolygonRenderComponent(Color.GREEN));
            addComponent(new BoundsRenderComponent2D(Color.RED));
        }

        addComponent(new Behaviour());

        transformComponent.setPosition(640, 360);
    }

    private static class Behaviour extends Component
    {
        private CollisionComponent2D collisionComponent;
        private SpriteComponent      spriteComponent;

        @Override
        protected void onCreate()
        {
            collisionComponent = entity.getComponent(CollisionComponent2D.class);
            spriteComponent = entity.getComponent(SpriteComponent.class);
        }

        @Override
        protected void onUpdate(float elapsedTime)
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_A))
                transformComponent.rotate(-45 * elapsedTime);

            if (Keyboard.isKeyDown(Keyboard.KEY_D))
                transformComponent.rotate(45 * elapsedTime);

            Vector3 pos = transformComponent.getPosition();
            ((OrthoCam) Camera.CURRENT).center(pos.x, pos.y);

            spriteComponent.tint.set(Color.BLACK);

            if (Touch.isAnyFingerDown())
            {
                Vector2 worldMouse = Util.getMouseInView();

                if (collisionComponent.polygon.contains(worldMouse))
                    spriteComponent.tint.set(Color.GRAY);
            }
        }
    }
}
