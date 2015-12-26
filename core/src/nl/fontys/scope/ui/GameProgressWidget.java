package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.Player;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.util.Colors;

public class GameProgressWidget extends Actor {

    private static final float BORDER_PADDING = 12f;

    private Player player;

    private NinePatch border, fill;

    private Label name;

    public GameProgressWidget(Player player) {
        this.player = player;
        setWidth(80f);
        setHeight(200f);
        border = GraphicsFactory.createNinePatch(Assets.Textures.BORDER, 20);
        border.setColor(Colors.UI.cpy());
        border.getColor().a = 0.8f;
        fill = GraphicsFactory.createNinePatch(Assets.Textures.FILL, 10);
        fill.setColor(Colors.UI.cpy());
        fill.getColor().a = 0.1f;
        name = new Label("player " + player.getNumber(), Styles.LABEL_PLAYER_NAME);
        name.getColor().a = 0.5f;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        border.draw(batch, getX(), getY(), getWidth(), getHeight());
        final float FILL_HEIGHT = getHeight() - BORDER_PADDING * 2;
        fill.draw(batch, getX() + BORDER_PADDING, getY() + BORDER_PADDING, getWidth() - BORDER_PADDING * 2, FILL_HEIGHT);
        fill.draw(batch, getX() + BORDER_PADDING, getY() + BORDER_PADDING, getWidth() - BORDER_PADDING * 2, FILL_HEIGHT * player.getGameProgress());
        name.setPosition(getX() + getWidth() / 2f - name.getPrefWidth() / 2f, getY() + getHeight() + 5f);
        name.draw(batch, parentAlpha);
    }
}
