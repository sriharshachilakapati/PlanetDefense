package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.ld38.states.PlayState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.Vector3;
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
    private float firingAngle;
    private boolean calculatedFiringAngle;

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
                if (!calculatedFiringAngle)
                {
                    Vector2 nearestPathPoint = PathComponent.PATH.get(0);

                    Vector3 position = transformComponent.getParent().getPosition();
                    Vector2 temp = Vector2.REUSABLE_STACK.pop();
                    temp.x = position.x;
                    temp.y = position.y;

                    for (Vector2 p : PathComponent.PATH)
                        if (p.distanceSquared(temp) < nearestPathPoint.distanceSquared(temp))
                            nearestPathPoint = p;

                    SilenceEngine.log.getRootLogger().info(nearestPathPoint);
                    SilenceEngine.log.getRootLogger().info(position);

                    firingAngle = temp.angle(nearestPathPoint);
                    Vector2.REUSABLE_STACK.push(temp);

                    calculatedFiringAngle = true;
                }

                Projectile projectile = new Projectile(firingAngle);
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
