package nl.fontys.scope.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aurelienribon.tweenengine.TweenEquations;
import nl.fontys.scope.Config;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.controls.KeyboardMenuSupport;
import nl.fontys.scope.controls.XboxMenuControllerSupport;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.controller.AIController;
import nl.fontys.scope.core.controller.CameraRotatingController;
import nl.fontys.scope.core.controller.LightingController;
import nl.fontys.scope.core.controller.RotationController;
import nl.fontys.scope.graphics.ShaderManager;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.Styles;
import nl.fontys.scope.ui.Tooltip;
import nl.fontys.scope.util.Colors;

public class MenuScreen extends AbstractScreen {

    private ShaderManager shaderManager = ShaderManager.getBaseInstance();
    private KeyboardMenuSupport keyboard;

    public MenuScreen(ScopeGame game) {
        super(game);
    }

    @Override
    protected void onShow() {
        final int ENERGY_COUNT = 4;
        final float MIN_DISTANCE = 55;
        final float MAX_DISTANCE = 60;
        Vector3 tmp = new Vector3();

        for (int i = 0; i < ENERGY_COUNT; ++i) {
            tmp.setToRandomDirection();
            tmp.setLength((float) (MIN_DISTANCE + Math.random() * (MAX_DISTANCE - MIN_DISTANCE)));
            GameObject energy = factory.createEnergy(tmp.x, tmp.y, tmp.z);
            LightingController lightingController = new LightingController(world.getLightingManager());
            lightingController.setStrength(925);
            world.addController(energy, lightingController);
            world.addController(energy, new RotationController(15f));
        }

        GameObject planet = factory.createPlanet(10f);
        world.addController(planet, new CameraRotatingController(100f, world.getCamera(), planet));
    }

    @Override
    protected void onUpdate(float delta) {
        keyboard.update(delta);
        Music music = AssetManager.getMusic(Assets.Musics.MAIN_THEME);
        if (!music.isPlaying()) {
            music.setLooping(true);
            music.play();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    protected void onCreateStage(Stage stage) {
        Table layout = new Table();
        layout.setFillParent(true);
        Image logo = new Image(new Sprite(AssetManager.getTexture(Assets.Textures.LOGO)));
        layout.center().add(logo).padBottom(20f);
        layout.row();
        ButtonMenu menu = new ButtonMenu(tweenManager, Controllers.getControllers().size > 0);
        menu.add(Bundle.general.get(Messages.MENU_NEW_GAME), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new CreateGameScreen(game));
            }
        });
        menu.add(Bundle.general.get(Messages.MENU_JOIN_GAME), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                setScreen(new JoinGameScreen(game));
            }
        });
        menu.add("singleplayer", new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                IngameScreen screen = new IngameScreen(game, new World(), new IngameScreen.IngameInitializer() {
                    @Override
                    public void initialize(IngameScreen screen) {
                        PlayerManager playerManager = screen.getPlayerManager();
                        Player player = playerManager.addPlayer();
                        player.setAI(true);
                        screen.world.addController(player.getShip(), new AIController(player));
                    }
                });
                setScreen(screen);
            }
        });
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
        keyboard = new KeyboardMenuSupport(menu);
        controllerManager.registerSupport(new XboxMenuControllerSupport(menu));
    }
}
