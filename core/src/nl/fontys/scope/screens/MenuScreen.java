package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import nl.fontys.scope.Config;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.logic.CameraRotatingLogic;
import nl.fontys.scope.core.logic.LightingLogic;
import nl.fontys.scope.core.logic.RotateAroundLogic;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.Styles;

public class MenuScreen extends AbstractScreen implements ExitHandler {

    private ButtonMenu menu;

    public MenuScreen(ScopeGame game) {
        super(game);
    }

    @Override
    protected void onShow() {
        final int ENERGY_COUNT = 4;
        final float MIN_DISTANCE = 34;
        final float MAX_DISTANCE = 40;
        Vector3 tmp = new Vector3();

        for (int i = 0; i < ENERGY_COUNT; ++i) {
            tmp.setToRandomDirection();
            tmp.setLength((float) (MIN_DISTANCE + Math.random() * (MAX_DISTANCE - MIN_DISTANCE)));
            GameObject energy = factory.createEnergy(tmp.x, tmp.y, tmp.z);
            LightingLogic lightingController = new LightingLogic(world.getLightingManager(), Color.LIME);
            lightingController.setStrength(30);
            world.addLogic(energy, lightingController);
            world.addLogic(energy, new RotateAroundLogic(15f));
        }

        GameObject planet = factory.createPlanet(14f);
        world.addLogic(planet, new CameraRotatingLogic(100f, world.getCamera(), planet));
    }

    @Override
    protected void onUpdate(float delta) {
        Music music = AssetManager.getMusic(Assets.Musics.MAIN_THEME);
        if (!music.isPlaying() && Config.MUSIC_ENABLED) {
            music.setLooping(true);
            music.play();
        }
    }

    @Override
    protected void onCreateStage(Stage stage) {
        Table layout = new Table();
        layout.setFillParent(true);
        Image logo = new Image(new Sprite(AssetManager.getTexture(Assets.Textures.LOGO)));
        layout.center().add(logo).padBottom(20f);
        layout.row();
        menu = new ButtonMenu(tweenManager, Controllers.getControllers().size > 0);
        menu.add(Bundle.general.get(Messages.MENU_SINGLE_PLAYER), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new SingleplayerScreen(game));
            }
        });
        menu.add(Bundle.general.get(Messages.MENU_NEW_GAME), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new CreateGameScreen(game));
            }
        }).setDisabled(!Config.NETWORKING_ENABLED);
        menu.add(Bundle.general.get(Messages.MENU_JOIN_GAME), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new JoinGameScreen(game));
            }
        }).setDisabled(!Config.NETWORKING_ENABLED);
        layout.add(menu).padBottom(30f).row();
        Label version = new Label("version " + Config.APP_VERSION, Styles.LABEL_VERSION);
        layout.row();
        layout.add(version);
        Label credits = new Label(Bundle.general.get(Messages.CREDITS), Styles.LABEL_CREDITS);
        layout.row();
        layout.add(credits);
        credits = new Label(Bundle.general.get(Messages.CREDITS_ADDITION), Styles.LABEL_CREDITS);
        layout.row();
        layout.add(credits);
        stage.addActor(layout);
    }

    @Override
    public void exit() {
        Gdx.app.exit();
    }

    @Override
    protected ButtonMenu getMenu() {
        return menu;
    }

    @Override
    protected ExitHandler getExitHandler() {
        return this;
    }
}
