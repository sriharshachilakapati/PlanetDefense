package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.ld38.Util;
import com.shc.ld38.states.PlayState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.input.Touch;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.math.Vector3;
import com.shc.silenceengine.scene.Component;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.components.CollisionComponent2D;
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
        addComponent(new Behaviour());

        transformComponent.setPosition(0, 0);
    }

    private static class Behaviour extends Component
    {
        private int levelCurrent;

        private float   firingAngle;
        private boolean calculatedFiringAngle;

        private SpriteComponent      spriteComponent;
        private CollisionComponent2D collisionComponent;

        @Override
        protected void onCreate()
        {
            spriteComponent = entity.getComponent(SpriteComponent.class);
            levelCurrent = 1;

            GameTimer projectileTimer = new GameTimer(2, TimeUtils.Unit.SECONDS);
            projectileTimer.setCallback(() ->
            {
                if (entity.isDestroyed())
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

                        firingAngle = temp.angle(nearestPathPoint) + 180;
                        Vector2.REUSABLE_STACK.push(temp);

                        calculatedFiringAngle = true;
                    }

                    Projectile projectile = new Projectile(firingAngle);
                    projectile.transformComponent.setPosition(transformComponent.getParent().getPosition());

                    PlayState.scene.addEntity(projectile);
                });

                if (!entity.isDestroyed())
                {
                    projectileTimer.setTime(MathUtils.randomRange(1, 5 - levelCurrent), TimeUtils.Unit.SECONDS);
                    projectileTimer.start();
                }
            });
            projectileTimer.start();
        }

        @Override
        protected void onUpdate(float elapsedTime)
        {
            if (Touch.isFingerTapped(Touch.FINGER_0) && levelCurrent < 3)
            {
                Vector2 pos = Util.getMouseInView();

                if (collisionComponent == null)
                    collisionComponent = transformComponent.getParent().getEntity().getComponent(CollisionComponent2D.class);

                if (collisionComponent.polygon.contains(pos))
                {
                    String color = "green";
                    int price = 2000;
                    if (levelCurrent + 1 == 3)
                    {
                        color = "red";
                        price = 4000;
                    }

                    if (PlayState.money < price)
                        return;

                    // Ask for confirmation
                    if (!SilenceEngine.display.confirm("Upgrade to " + color + " turret for " + price + " bucks?"))
                        return;

                    PlayState.money -= price;
                    levelCurrent++;

                    spriteComponent.sprite = new Sprite(levelCurrent == 2 ? Resources.Textures.TURRET_GREEN : Resources.Textures.TURRET_RED);
                }
            }
        }
    }
}
