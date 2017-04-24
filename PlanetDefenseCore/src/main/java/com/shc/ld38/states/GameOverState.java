package com.shc.ld38.states;

import com.shc.ld38.PlanetDefense;
import com.shc.ld38.Resources;
import com.shc.silenceengine.core.GameState;
import com.shc.silenceengine.core.SilenceEngine;
import com.shc.silenceengine.graphics.IGraphicsDevice;
import com.shc.silenceengine.graphics.SceneRenderSystem;
import com.shc.silenceengine.graphics.Sprite;
import com.shc.silenceengine.graphics.fonts.BitmapFontRenderer;
import com.shc.silenceengine.input.Touch;
import com.shc.silenceengine.scene.Entity;
import com.shc.silenceengine.scene.Scene;
import com.shc.silenceengine.scene.components.SpriteComponent;

import static com.shc.ld38.PlanetDefense.*;

/**
 * @author Sri Harsha Chilakapati
 */
public class GameOverState extends GameState
{
    private Scene scene;

    @Override
    public void onEnter()
    {
        scene = new Scene();

        Entity logo = new Entity();
        logo.addComponent(new SpriteComponent(new Sprite(Resources.Textures.LOGO)));

        logo.transformComponent.setPosition(WIDTH / 2, HEIGHT / 2);

        scene.addEntity(logo);
        scene.registerRenderSystem(new SceneRenderSystem());
    }

    @Override
    public void update(float delta)
    {
        scene.update(delta);

        if (Touch.isFingerTapped(Touch.FINGER_0))
            PlanetDefense.INSTANCE.setGameState(new PlayState());
    }

    @Override
    public void render(float delta)
    {
        hudCam.apply();
        scene.render(delta);

        BitmapFontRenderer fontRenderer = IGraphicsDevice.Renderers.bitmapFont;
        IGraphicsDevice.Programs.font.use();

        fontRenderer.begin();
        {
            String message;

            if (SilenceEngine.display.getPlatform() == SilenceEngine.Platform.ANDROID)
                message = "Touch anywhere to replay the game";
            else
                message = "Click anywhere to replay the game";

            float centerX = WIDTH / 2 - Resources.Fonts.DEFAULT.getWidth(message) / 2;
            fontRenderer.render(Resources.Fonts.DEFAULT, message, centerX, HEIGHT - 100);

            message = "A game made by Sri Harsha Chilakapati";
            centerX = WIDTH / 2 - Resources.Fonts.DEFAULT.getWidth(message) / 2;
            fontRenderer.render(Resources.Fonts.DEFAULT, message, centerX, 30);
        }
        fontRenderer.end();
    }
}
