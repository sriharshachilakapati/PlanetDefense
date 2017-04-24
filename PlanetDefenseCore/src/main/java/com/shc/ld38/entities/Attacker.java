package com.shc.ld38.entities;

import com.shc.ld38.Resources;
import com.shc.ld38.states.PlayState;
import com.shc.silenceengine.collision.CollisionTag;
import com.shc.silenceengine.core.Game;
import com.shc.silenceengine.graphics.Color;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.graphics.opengl.Texture;
import com.shc.silenceengine.math.geom2d.Polygon;
import com.shc.silenceengine.scene.Component;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.components.BoundsRenderComponent2D;
import com.shc.silenceengine.scene.components.CollisionComponent2D;
import com.shc.silenceengine.scene.components.PolygonRenderComponent;
import com.shc.silenceengine.scene.components.SpriteComponent;

/**
 * @author Sri Harsha Chilakapati
 */
public class Attacker extends Entity
{
    public static final CollisionTag COLLISION_TAG = new CollisionTag();

    private Type type;

    public Attacker(Type type, float x, float y)
    {
        addComponent(new SpriteComponent(new Sprite(type.texture)));
        addComponent(new CollisionComponent2D(COLLISION_TAG, type.polygon.copy(), this::attackerCollision));

        if (Game.DEVELOPMENT)
        {
            addComponent(new BoundsRenderComponent2D(Color.RED));
            addComponent(new PolygonRenderComponent(Color.GREEN));
        }

        addComponent(new PathComponent(type.rotatesInPath));

        if (type == Type.ALIEN)
            addComponent(new AlienBehaviour());

        this.type = type;
        transformComponent.setPosition(x, y);
    }

    private void attackerCollision(CollisionComponent2D other)
    {
        if (other.tag == Projectile.COLLISION_TAG)
        {
            destroy();
            other.getEntity().destroy();

            // Reward money
            switch (type)
            {
                case PLANE:
                    PlayState.money += 500;
                    break;

                case UFO:
                    PlayState.money += 1000;
                    break;

                case ALIEN:
                    PlayState.money += 1500;
                    break;
            }
        }
    }

    private static class AlienBehaviour extends Component
    {
        @Override
        protected void onUpdate(float elapsedTime)
        {
            transformComponent.rotate(180 * elapsedTime);
        }
    }

    public enum Type
    {
        PLANE(true, Resources.Textures.PLANE, Resources.Polygons.PLANE),
        UFO(false, Resources.Textures.UFO, Resources.Polygons.UFO),
        ALIEN(false, Resources.Textures.ALIEN, Resources.Polygons.ALIEN);

        Type(boolean rotatesInPath, Texture texture, Polygon polygon)
        {
            this.rotatesInPath = rotatesInPath;
            this.texture = texture;
            this.polygon = polygon;
        }

        private boolean rotatesInPath;
        private Texture texture;
        private Polygon polygon;
    }
}
