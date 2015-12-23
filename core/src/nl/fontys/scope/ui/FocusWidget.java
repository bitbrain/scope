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

import java.util.Collection;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.util.Colors;

public class FocusWidget extends Actor {

    private PlayerManager playerManager;

    private PerspectiveCamera camera;

    private NinePatch focusPatch;

    private Sprite focusTarget;

    private Vector3 tmp = new Vector3();

    private Rectangle rect = new Rectangle();

    public FocusWidget(PerspectiveCamera camera, PlayerManager playerManager) {
        this.camera = camera;
        this.playerManager = playerManager;
        focusPatch = GraphicsFactory.createNinePatch(Assets.Textures.FOCUS, 35);
        focusTarget = new Sprite(AssetManager.getTexture(Assets.Textures.FOCUS_TARGET));
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
        GameObject ship = player.getShip();
        final float SIZE = 75f + 5000f / (float)tmp.set(ship.getPosition()).sub(camera.position.x, camera.position.y, camera.position.z).scl(0.225f).len2();
        Vector2 cameraPos2D = mapToCamera(ship);
        float angle = 180f - cameraPos2D.angle();
        if (bindToScreen(cameraPos2D, 120f)) {
            focusTarget.setColor(Colors.UI);
            focusTarget.setRotation(angle);
            focusTarget.setPosition(cameraPos2D.x - SIZE / 2f, cameraPos2D.y - SIZE / 2f);
            focusTarget.setSize(75f, 75f);
            focusTarget.setOrigin(75f / 2f, 75f / 2f);
            focusTarget.draw(batch, parentAlpha);
        } else {
            focusPatch.setColor(Colors.UI);
            focusPatch.draw(batch, cameraPos2D.x - SIZE / 2f, cameraPos2D.y - SIZE / 2f, SIZE, SIZE);
        }
    }

    private Vector2 mapToCamera(GameObject ship) {
        tmp.set(ship.getPosition());
        camera.project(tmp, 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return new Vector2(tmp.x, tmp.y);
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
