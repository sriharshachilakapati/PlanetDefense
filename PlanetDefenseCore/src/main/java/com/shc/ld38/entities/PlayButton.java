package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.ld38.Util;
import com.shc.ld38.states.PlayState;
import com.shc.silenceengine.collision.CollisionTag;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.input.Touch;
import com.shc.silenceengine.math.Vector2;
import com.shc.silenceengine.scene.Component;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.components.BoundsRenderComponent2D;
import com.shc.silenceengine.scene.components.CollisionComponent2D;
import com.shc.silenceengine.scene.components.PolygonRenderComponent;
import com.shc.silenceengine.scene.components.SpriteComponent;

/**
 * @author Sri Harsha Chilakapati
 */
public class PlayButton extends Entity
{
    public static boolean enabled;

    public PlayButton()
    {
        transformComponent.setPosition(PlayState.WIDTH - 40, 30);

        addComponent(new SpriteComponent(Resources.Sprites.PLAY_ENABLED));
        addComponent(new CollisionComponent2D(new CollisionTag(), Resources.Polygons.PLAY.copy()));

        if (Game.DEVELOPMENT)
        {
            addComponent(new PolygonRenderComponent(Color.GREEN));
            addComponent(new BoundsRenderComponent2D(Color.RED));
        }

        addComponent(new Behaviour());
    }

    private static class Behaviour extends Component
    {
        private SpriteComponent      spriteComponent;
        private CollisionComponent2D collisionComponent;

        @Override
        protected void onCreate()
        {
            spriteComponent = entity.getComponent(SpriteComponent.class);
            collisionComponent = entity.getComponent(CollisionComponent2D.class);
        }

        @Override
        protected void onUpdate(float elapsedTime)
        {
            if (!enabled)
            {
                spriteComponent.sprite = Resources.Sprites.PLAY_DISABLED;
                return;
            }

            Vector2 pos = Util.getMouseInView();

            if (collisionComponent.polygon.contains(pos))
            {
                spriteComponent.sprite = Resources.Sprites.PLAY_HOVER;

                if (Touch.isFingerTapped(Touch.FINGER_0))
                {
                    PlayState.generateNewWave();
                    enabled = false;
                }
            }
            else
                spriteComponent.sprite = Resources.Sprites.PLAY_ENABLED;
        }
    }
}
