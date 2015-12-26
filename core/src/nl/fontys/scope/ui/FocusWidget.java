package nl.fontys.scope.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenEquation;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.tweens.ValueTween;
import nl.fontys.scope.util.Colors;
import nl.fontys.scope.util.ValueProvider;

public class FocusWidget extends Actor {

    private static final float TRANSITION_TIME = 1f;

    private static class FocusData {
        public ValueProvider value;
        public boolean visible;
        public FocusData() {
            value = new ValueProvider();
        }
    }

    private PlayerManager playerManager;

    private PerspectiveCamera camera;

    private NinePatch focusPatch;

    private Sprite focusTarget;

    private Vector3 vecTemp3D = new Vector3();

    private Vector2 vecTemp2D = new Vector2();

    private Rectangle rect = new Rectangle();

    private Map<Player, FocusData> animationData = new HashMap<Player, FocusData>();

    private Label playerName;

    private TweenManager tweenManager;

    public FocusWidget(PerspectiveCamera camera, PlayerManager playerManager, TweenManager tweenManager) {
        this.camera = camera;
        this.playerManager = playerManager;
        this.tweenManager = tweenManager;
        focusPatch = GraphicsFactory.createNinePatch(Assets.Textures.FOCUS, 35);
        focusTarget = new Sprite(AssetManager.getTexture(Assets.Textures.FOCUS_TARGET));
        playerName = new Label("unknown", Styles.LABEL_PLAYER_NAME);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Collection<Player> players = playerManager.getPlayers();
        for (Player player : players) {
            if (!player.isCurrentPlayer()) {
                drawFocus(player, batch, parentAlpha);
            }
        }
    }

    private void drawFocus(Player player, Batch batch, float parentAlpha) {
        if (!animationData.containsKey(player)) {
            animationData.put(player, new FocusData());
        }
        FocusData focusData = animationData.get(player);
        GameObject ship = player.getShip();
        final float SIZE = 75f + 5000f / (float) vecTemp3D.set(ship.getPosition()).sub(camera.position.x, camera.position.y, camera.position.z).scl(0.225f).len2();
        Vector2 cameraPos2D = mapToCamera(ship);
        if (bindToScreen(cameraPos2D, 80f)) {
            if (focusData.visible) {
                focusData.visible = false;
                tweenManager.killTarget(focusData.value);
                Tween.to(focusData.value, ValueTween.VALUE, TRANSITION_TIME).target(0.3f).ease(TweenEquations.easeOutCubic).start(tweenManager);
            }
            parentAlpha = focusData.value.getValue();
            vecTemp2D.set(cameraPos2D.x - Gdx.graphics.getWidth() / 2f, cameraPos2D.y - Gdx.graphics.getHeight() / 2f);
            final float angle = vecTemp2D.angle() - 45f - 180f;
            final float posX = cameraPos2D.x - SIZE / 2f;
            final float posY = cameraPos2D.y - SIZE / 2f;
            focusTarget.setColor(Colors.UI);
            focusTarget.setRotation(angle);
            focusTarget.setPosition(posX, posY);
            focusTarget.setSize(75f, 75f);
            focusTarget.setOrigin(75f / 2f, 75f / 2f);
            focusTarget.draw(batch, parentAlpha);
            playerName.setText("P" + player.getNumber());
            playerName.setFontScale(1f);
            playerName.setPosition(posX + (75f / 2f) - (playerName.getPrefWidth() / 2f), posY + (75f / 2f) - (playerName.getPrefHeight() / 2f));
            playerName.draw(batch, parentAlpha);
        } else {
            if (!focusData.visible) {
                focusData.visible = true;
                tweenManager.killTarget(focusData.value);
                Tween.to(focusData.value, ValueTween.VALUE, TRANSITION_TIME).target(1f).ease(TweenEquations.easeOutCubic).start(tweenManager);
            }
            parentAlpha = focusData.value.getValue();
            final float posX = cameraPos2D.x - SIZE / 2f;
            final float posY = cameraPos2D.y - SIZE / 2f;
            final float NAME_PADDING = (SIZE * SIZE) / 360f;
            focusPatch.setColor(Colors.UI.cpy());
            focusPatch.getColor().a = parentAlpha;
            focusPatch.draw(batch, posX, posY, SIZE, SIZE);
            playerName.setText("Player " + player.getNumber());
            playerName.setFontScale(SIZE / 75f);
            playerName.setPosition(posX + (SIZE / 2f) - (playerName.getPrefWidth() / 2f), posY + SIZE + NAME_PADDING);
            playerName.draw(batch, parentAlpha);
        }
    }

    private Vector2 mapToCamera(GameObject ship) {
        vecTemp3D.set(ship.getPosition());
        camera.project(vecTemp3D, 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        vecTemp2D.set(vecTemp3D.x, vecTemp3D.y);
        return vecTemp2D.cpy();
    }

    private boolean bindToScreen(Vector2 pos, float padding) {
        rect.set(padding, padding, Gdx.graphics.getWidth() - padding * 2f, Gdx.graphics.getHeight() - padding * 2f);
        boolean changed = false;
        if (pos.x < rect.getX()) {
            pos.x = padding;
            changed = true;
        } else if (pos.x > rect.getX() + rect.getWidth()) {
            pos.x = rect.getX() + rect.getWidth();
            changed = true;
        }
        if (pos.y < rect.getY()) {
            pos.y = padding;
            changed = true;
        } else if (pos.y > rect.getY() + rect.getHeight()) {
            pos.y = rect.getY() + rect.getHeight();
            changed = true;
        }
        return changed;
    }
}
