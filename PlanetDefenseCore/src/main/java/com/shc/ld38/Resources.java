package com.shc.ld38;

import com.shc.ld38.states.PlayState;
import com.shc.silenceengine.core.ResourceLoader;
import com.shc.silenceengine.graphics.Image;
import com.shc.silenceengine.graphics.opengl.Texture;
import com.shc.silenceengine.io.FilePath;
import com.shc.silenceengine.math.geom2d.Polygon;
import com.shc.silenceengine.utils.ResourceLoadingState;

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

            PlanetDefense.INSTANCE.setGameState(new PlayState());
        }));
    }

    public static class Polygons
    {
        public static Polygon PLANET;
        public static Polygon ASTEROID;
    }

    public static class Textures
    {
        public static Texture PLANET;
        public static Texture ASTEROID;
    }
}
