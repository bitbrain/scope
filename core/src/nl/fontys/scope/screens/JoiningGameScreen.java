package nl.fontys.scope.screens;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquations;
import nl.fontys.scope.ScopeGame;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.core.World;
import nl.fontys.scope.core.logic.CameraRotatingLogic;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.i18n.Bundle;
import nl.fontys.scope.i18n.Messages;
import nl.fontys.scope.net.client.GameClient;
import nl.fontys.scope.net.server.Responses;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.tweens.ActorTween;
import nl.fontys.scope.ui.ButtonMenu;
import nl.fontys.scope.ui.ExitHandler;
import nl.fontys.scope.ui.Styles;
import nl.fontys.scope.util.Colors;

public class JoiningGameScreen extends AbstractScreen implements ExitHandler {

    private IngameScreen ingameScreen;

    private Events events = Events.getInstance();

    private String gameName;

    private GameClient client;

    private GameClient.GameClientHandler handler;

    private boolean joined;

    private boolean ready;
    private Label caption;

    public JoiningGameScreen(ScopeGame game, String name) {
        super(game);
        this.gameName = name;
    }

    @Override
    protected void onShow() {
        World world = new World();
        this.ingameScreen = new IngameScreen(game, world, false) {
            @Override
            protected void onShow() {
                super.onShow();
                // Register again the client here
                client.setEvents(Events.getInstance());
            }
        };
        events.register(this);
        GameObject planet = factory.createPlanet(30f);
        world.addLogic(new CameraRotatingLogic(800f, world.getCamera(), planet));
        client = new GameClient(events, gameName, world, ingameScreen.getPlayerManager());
        handler = new GameClient.GameClientHandler() {
            @Override
            public void onGameClosed(Responses.GameClosed closed) {
                System.out.println("Game closed..");
                exit();
            }

            @Override
            public void onGameReady(Responses.GameReady ready) {
                JoiningGameScreen.this.ready = true;
                setScreen(ingameScreen);
            }

            @Override
            public void onClientLeft(Responses.ClientLeft left) {
                if (joined) {
                    caption.setText(Bundle.general.format(Messages.WAITING_FOR_OTHER_PLAYERS, left.getCurrentClients(), left.getMaxClients()));
                }
                System.out.println("Client left..");
            }

            @Override
            public void onClientJoined(Responses.ClientJoined joined) {
                if (ingameScreen.getPlayerManager().isCurrentPlayer(joined.getClientId())) {
                    JoiningGameScreen.this.joined = true;
                }
                if (JoiningGameScreen.this.joined) {
                    caption.setText(Bundle.general.format(Messages.WAITING_FOR_OTHER_PLAYERS, joined.getCurrentClients(), joined.getMaxClients()));
                }
                System.out.println("Client joined: " + joined.getClientId());
            }

            @Override
            public void onConnectionFailed() {
                MenuScreen screen = new MenuScreen(game);
                screen.getTooltipQueue().add(Messages.ERROR_SERVER_NOT_REACHABLE, Styles.LABEL_ERROR);
                JoiningGameScreen.this.setScreen(screen);
            }
        };
        client.addHandler(handler);
        client.connect(false);
    }

    @Override
    protected void onUpdate(float delta) {

    }

    @Override
    protected void onCreateStage(Stage stage) {
        Table layout = new Table();
        layout.setFillParent(true);
        caption = new Label(Bundle.general.format(Messages.JOINING_GAME, gameName), Styles.LABEL_CAPTION);
        Tween
          .to(caption, ActorTween.ALPHA, 0.8f)
          .target(0.7f)
          .ease(TweenEquations.easeInCubic)
          .repeatYoyo(Tween.INFINITY, 0f)
          .start(tweenManager);
        Image image = new Image(new Sprite(GraphicsFactory.createNinePatch(Assets.Textures.FOCUS, 15).getTexture()));
        image.setOrigin(image.getWidth() / 2f, image.getHeight() / 2f);
        image.setColor(Colors.PRIMARY);
        Tween
           .to(image, ActorTween.ROTATION, 0.8f)
           .target(-360f)
           .ease(TweenEquations.easeOutCubic)
           .repeat(Tween.INFINITY, 0f)
           .start(tweenManager);
        Tween
           .to(image, ActorTween.ALPHA, 0.8f)
           .target(0.4f)
           .ease(TweenEquations.easeInCubic)
           .repeatYoyo(Tween.INFINITY, 0f)
           .start(tweenManager);
        layout.center().add(image).row();
        layout.add(caption);
        layout.row();
        ButtonMenu menu = new ButtonMenu(tweenManager);
        menu.add(Bundle.general.get(Messages.ABORT), new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                client.leaveCurrentGame();
                setScreen(new MenuScreen(game));
            }
        });
        layout.add(menu).padTop(60);
        stage.addActor(layout);
    }

    @Override
    public void exit() {
        setScreen(new MenuScreen(game));
    }

    @Override
    protected ExitHandler getExitHandler() {
        return this;
    }

    @Override
    protected void onDispose() {
        if (!ready) {
            client.leaveCurrentGame();
        }
        client.removeHandler(handler);
    }
}