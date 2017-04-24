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
import com.shc.silenceengine.utils.GameTimer;
import com.shc.silenceengine.utils.MathUtils;
import com.shc.silenceengine.utils.TaskManager;
import com.shc.silenceengine.utils.TimeUtils;

/**
 * @author Sri Harsha Chilakapati
 */
public class Projectile extends Entity
{
    public static final CollisionTag COLLISION_TAG = new CollisionTag();

    public Projectile(float angle)
    {
        SpriteComponent spriteComponent = new SpriteComponent(new Sprite(Resources.Textures.PROJECTILE));
        spriteComponent.layer = -2;

        addComponent(spriteComponent);
        addComponent(new CollisionComponent2D(COLLISION_TAG, Resources.Polygons.PROJECTILE.copy()));

        if (Game.DEVELOPMENT)
        {
            addComponent(new PolygonRenderComponent(Color.GREEN));
            addComponent(new BoundsRenderComponent2D(Color.RED));
        }

        addComponent(new Behaviour(angle));
    }

    private static class Behaviour extends Component
    {
        private float tx, ty;

        private Behaviour(float angle)
        {
            tx = 4 * MathUtils.cos(angle);
            ty = 4 * MathUtils.sin(angle);

            float finalAngle = angle;
            TaskManager.runOnUpdate(() -> transformComponent.setRotation(finalAngle));
        }

        @Override
        protected void onCreate()
        {
            GameTimer deathTimer = new GameTimer(10, TimeUtils.Unit.SECONDS);
            deathTimer.setCallback(entity::destroy);
            deathTimer.start();
        }

        @Override
        protected void onUpdate(float elapsedTime)
        {
            transformComponent.translate(tx, ty);
        }
    }
}
