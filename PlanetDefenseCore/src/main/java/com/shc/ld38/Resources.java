package com.shc.ld38;

import com.shc.ld38.states.IntroState;
import com.shc.silenceengine.core.ResourceLoader;
import com.shc.silenceengine.graphics.Image;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.graphics.SpriteSheet;
import com.shc.silenceengine.graphics.fonts.BitmapFont;
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
        long texTurretsID = loader.define(Texture.class, FilePath.getResourceFile("textures/sheet_turrets.png"));
        long texProjectileID = loader.define(Image.class, FilePath.getResourceFile("textures/projectile.png"));
        long texPlaneID = loader.define(Image.class, FilePath.getResourceFile("textures/plane.png"));
        long texUFOID = loader.define(Image.class, FilePath.getResourceFile("textures/ufo.png"));
        long texAlienID = loader.define(Image.class, FilePath.getResourceFile("textures/alien.png"));
        long texPlayBtnID = loader.define(Image.class, FilePath.getResourceFile("textures/playButton.png"));
        long texLogoID = loader.define(Texture.class, FilePath.getResourceFile("textures/logo.png"));

        long fontDefID = loader.define(BitmapFont.class, FilePath.getResourceFile("engine_resources/fonts/roboto32px.fnt"));

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

            Image projectileImage = loader.get(texProjectileID);

            Polygons.PROJECTILE = Polygon.createConvexHull(projectileImage);
            Textures.PROJECTILE = Texture.fromImage(projectileImage);

            projectileImage.dispose();

            Image planeImage = loader.get(texPlaneID);

            Polygons.PLANE = Polygon.createConvexHull(planeImage);
            Textures.PLANE = Texture.fromImage(planeImage);

            planeImage.dispose();

            Image ufoImage = loader.get(texUFOID);

            Polygons.UFO = Polygon.createConvexHull(ufoImage);
            Textures.UFO = Texture.fromImage(ufoImage);

            ufoImage.dispose();

            Image alienImage = loader.get(texAlienID);

            Polygons.ALIEN = Polygon.createConvexHull(alienImage);
            Textures.ALIEN = Texture.fromImage(alienImage);

            alienImage.dispose();

            Image playImage = loader.get(texPlayBtnID);

            Polygons.PLAY = Polygon.createConvexHull(playImage, 0, 0, 50, 34);
            Texture playTexture = Texture.fromImage(playImage);

            SpriteSheet playButtonSheet = new SpriteSheet(playTexture, 50, 34);

            Sprites.PLAY_ENABLED = new Sprite(playButtonSheet.getCell(0, 0));
            Sprites.PLAY_HOVER = new Sprite(playButtonSheet.getCell(0, 1));
            Sprites.PLAY_DISABLED = new Sprite(playButtonSheet.getCell(0, 2));

            playImage.dispose();

            Textures.LOGO = loader.get(texLogoID);

            Fonts.DEFAULT = loader.get(fontDefID);

            PlanetDefense.INSTANCE.setGameState(new IntroState());
        }));
    }

    public static class Polygons
    {
        public static Polygon PLAY;
        public static Polygon ALIEN;
        public static Polygon UFO;
        public static Polygon PLANET;
        public static Polygon ASTEROID;
        public static Polygon PROJECTILE;
        public static Polygon PLANE;
    }

    public static class Textures
    {
        public static Texture LOGO;
        public static Texture ALIEN;
        public static Texture UFO;
        public static Texture PLANET;
        public static Texture ASTEROID;
        public static Texture TURRET_BLUE;
        public static Texture TURRET_GREEN;
        public static Texture TURRET_RED;
        public static Texture PROJECTILE;
        public static Texture PLANE;
    }

    public static class Sprites
    {
        public static Sprite PLAY_ENABLED;
        public static Sprite PLAY_HOVER;
        public static Sprite PLAY_DISABLED;
    }

    public static class Fonts
    {
        public static BitmapFont DEFAULT;
    }
}
