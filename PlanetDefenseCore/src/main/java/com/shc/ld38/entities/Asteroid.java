package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.ld38.Util;
import com.shc.ld38.states.PlayState;
import com.shc.silenceengine.collision.CollisionTag;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.input.Touch;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.scene.Component;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.components.BoundsRenderComponent2D;
import com.shc.silenceengine.scene.components.CollisionComponent2D;
import com.shc.silenceengine.scene.components.PolygonRenderComponent;
import com.shc.silenceengine.scene.components.SpriteComponent;
import com.shc.silenceengine.utils.MathUtils;
import com.shc.silenceengine.utils.TaskManager;

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
        private CollisionComponent2D collisionComponent;

        private boolean alreadyOccupied;

        @Override
        protected void onCreate()
        {
            collisionComponent = entity.getComponent(CollisionComponent2D.class);
            transformComponent.rotate(MathUtils.randomRange(0, 360));
        }

        @Override
        protected void onUpdate(float elapsedTime)
        {
            transformComponent.rotate(5 * elapsedTime);

            if (Touch.isFingerTapped(Touch.FINGER_0) && !alreadyOccupied)
            {
                Vector2 pos = Util.getMouseInView();
                if (collisionComponent.polygon.contains(pos))
                {
                    Turret turret = new Turret();
                    turret.transformComponent.setParent(transformComponent);
                    TaskManager.runOnRender(() -> PlayState.scene.addEntity(turret));

                    alreadyOccupied = true;
                }
            }
        }
    }
}
