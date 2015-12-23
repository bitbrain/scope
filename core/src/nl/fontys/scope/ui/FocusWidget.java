package nl.fontys.scope.ui;

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
        Vector3 point = new Vector3(camera.position);
        Vector3 distance = new Vector3(point).sub(ship.getPosition());
        Vector3 normal = new Vector3(camera.direction).nor();
        distance.dot(normal);
        Vector3 projectedPoint = point.sub(distance.x * normal.x, distance.y * normal.y, distance.z * normal.z);
        return new Vector2(projectedPoint.x, -projectedPoint.y);
    }
}
