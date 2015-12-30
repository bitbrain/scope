package nl.fontys.scope.ui;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import nl.fontys.scope.Config;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.graphics.GraphicsFactory;
import nl.fontys.scope.util.Colors;

public final class ActorShadow {

    private static NinePatch shadowPatch;

    public static void draw(Batch batch, Actor actor) {
        if (shadowPatch == null) {
            shadowPatch = GraphicsFactory.createNinePatch(Assets.Textures.SHADOW, 60);
            shadowPatch.setColor(Colors.lighten(Colors.BACKGROUND, 0.7f));
        }
        shadowPatch.getColor().a = 0.8f * actor.getColor().a;
        shadowPatch.draw(batch, actor.getX() - Config.UI_SHADOW_MARGIN, actor.getY() - Config.UI_SHADOW_MARGIN, actor.getWidth() + Config.UI_SHADOW_MARGIN * 2f, actor.getHeight() + Config.UI_SHADOW_MARGIN * 2f);
    }
}
