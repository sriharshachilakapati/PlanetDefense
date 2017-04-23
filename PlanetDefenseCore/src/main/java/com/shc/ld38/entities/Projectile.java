package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.silenceengine.collision.CollisionTag;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.scene.Component;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.components.CollisionComponent2D;
import com.shc.silenceengine.scene.components.SpriteComponent;
import com.shc.silenceengine.utils.GameTimer;
import com.shc.silenceengine.utils.MathUtils;
import com.shc.silenceengine.utils.TimeUtils;

/**
 * @author Sri Harsha Chilakapati
 */
public class Projectile extends Entity
{
    public static final CollisionTag COLLISION_TAG = new CollisionTag();

    public Projectile()
    {
        SpriteComponent spriteComponent = new SpriteComponent(new Sprite(Resources.Textures.PROJECTILE));
        spriteComponent.layer = -2;

        addComponent(spriteComponent);
        addComponent(new CollisionComponent2D(COLLISION_TAG, Resources.Polygons.PROJECTILE.copy()));
        addComponent(new Behaviour());
    }

    private static class Behaviour extends Component
    {
        private float tx, ty;

        @Override
        protected void onCreate()
        {
            final float angle = MathUtils.randomRange(0, 360);

            tx = 4 * MathUtils.cos(angle);
            ty = 4 * MathUtils.sin(angle);

            transformComponent.setRotation(angle);

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
