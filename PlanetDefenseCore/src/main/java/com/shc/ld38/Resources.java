package com.shc.ld38;

import com.shc.ld38.states.PlayState;
import com.shc.silenceengine.core.ResourceLoader;
import com.shc.silenceengine.graphics.Animation;
import com.shc.silenceengine.graphics.Image;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.graphics.SpriteSheet;
import com.shc.silenceengine.graphics.opengl.Texture;
import com.shc.silenceengine.io.FilePath;
import com.shc.silenceengine.math.geom2d.Polygon;
import com.shc.silenceengine.utils.ResourceLoadingState;
import com.shc.silenceengine.utils.TimeUtils;

/**
 * @author Sri Harsha Chilakapati
 */
public class Resources
{
    public static void load()
    {
        ResourceLoader loader = new ResourceLoader();

        long texPlanetID = loader.define(Image.class, FilePath.getResourceFile("textures/little_planet.png"));
        long texAsteroidID = loader.define(Image.class, FilePath.getResourceFile("textures/asteroid.png"));
        long texTurretsID = loader.define(Texture.class, FilePath.getResourceFile("textures/sheet_turrets.png"));
        long texSkyID = loader.define(Texture.class, FilePath.getResourceFile("textures/sky.png"));
        long texProjectileID = loader.define(Image.class, FilePath.getResourceFile("textures/projectile.png"));

        PlanetDefense.INSTANCE.setGameState(new ResourceLoadingState(loader, () ->
        {
            Image planetImage = loader.get(texPlanetID);

            Polygons.PLANET = Polygon.createConvexHull(planetImage);
            Textures.PLANET = Texture.fromImage(planetImage);

            planetImage.dispose();

            Image asteroidImage = loader.get(texAsteroidID);

            Polygons.ASTEROID = Polygon.createConvexHull(asteroidImage);
            Textures.ASTEROID = Texture.fromImage(asteroidImage);

            asteroidImage.dispose();

            Texture turretsTexture = loader.get(texTurretsID);
            SpriteSheet turrets = new SpriteSheet(turretsTexture, 47, 46);

            Textures.TURRET_BLUE = turrets.getCell(0, 0);
            Textures.TURRET_GREEN = turrets.getCell(0, 1);
            Textures.TURRET_RED = turrets.getCell(0, 2);

            Texture skyTexture = loader.get(texSkyID);
            SpriteSheet sky = new SpriteSheet(skyTexture, 611, 716/2);

            Animation skyAnim = new Animation();
            skyAnim.addFrame(sky.getCell(0, 0), 350, TimeUtils.Unit.MILLIS);
            skyAnim.addFrame(sky.getCell(1, 0), 350, TimeUtils.Unit.MILLIS);

            Animations.SKY = new Sprite(skyAnim);
            Animations.SKY.setEndCallback(Animations.SKY::start);
            Animations.SKY.start();

            Image projectileImage = loader.get(texProjectileID);

            Polygons.PROJECTILE = Polygon.createConvexHull(projectileImage);
            Textures.PROJECTILE = Texture.fromImage(projectileImage);

            projectileImage.dispose();

            PlanetDefense.INSTANCE.setGameState(new PlayState());
        }));
    }

    public static class Polygons
    {
        public static Polygon PLANET;
        public static Polygon ASTEROID;
        public static Polygon PROJECTILE;
    }

    public static class Textures
    {
        public static Texture PLANET;
        public static Texture ASTEROID;
        public static Texture TURRET_BLUE;
        public static Texture TURRET_GREEN;
        public static Texture TURRET_RED;
        public static Texture PROJECTILE;
    }

    public static class Animations
    {
        public static Sprite SKY;
    }
}
