package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.ld38.states.PlayState;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.components.SpriteComponent;
import com.shc.silenceengine.utils.GameTimer;
import com.shc.silenceengine.utils.MathUtils;
import com.shc.silenceengine.utils.TaskManager;
import com.shc.silenceengine.utils.TimeUtils;

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

        GameTimer projectileTimer = new GameTimer(2, TimeUtils.Unit.SECONDS);
        projectileTimer.setCallback(() ->
        {
            if (isDestroyed())
                return;

            TaskManager.runOnRender(() ->
            {
                Projectile projectile = new Projectile();
                projectile.transformComponent.setPosition(transformComponent.getParent().getPosition());

                PlayState.scene.addEntity(projectile);
            });

            if (!isDestroyed())
            {
                projectileTimer.setTime(MathUtils.randomRange(2, 5), TimeUtils.Unit.SECONDS);
                projectileTimer.start();
            }
        });
        projectileTimer.start();
    }
}
