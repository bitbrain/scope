package nl.fontys.scope.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Collection;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.util.Colors;

public class FocusWidget extends Actor {

    private PlayerManager playerManager;

    private PerspectiveCamera camera;

    private Sprite focusSprite;

    private Vector3 tmp = new Vector3();

    public FocusWidget(PerspectiveCamera camera, PlayerManager playerManager) {
        this.camera = camera;
        this.playerManager = playerManager;
        focusSprite = new Sprite(AssetManager.getTexture(Assets.Textures.FOCUS));
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
        Vector2 cameraVector = mapToCamera(ship);
        focusSprite.setPosition(cameraVector.x, cameraVector.y);
        focusSprite.setColor(Colors.UI);
        focusSprite.draw(batch, parentAlpha);
    }

    private Vector2 mapToCamera(GameObject ship) {
        tmp.set(ship.getPosition());
        camera.project(tmp, 0f, 0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        return new Vector2(tmp.x, tmp.y);
    }
}
