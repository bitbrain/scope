package nl.fontys.scope.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;

import nl.fontys.scope.core.PlayerManager;
import nl.fontys.scope.object.GameObject;

public class DebugWidget extends Table {

    private Label x, y, z, yaw, pitch, roll;

    private Label fps;

    private GameObject ship;

    public DebugWidget() {
        ship = PlayerManager.getCurrent().getShip();
        setFillParent(true);
        VerticalGroup shipStats = new VerticalGroup();
        shipStats.setFillParent(true);

        x = new Label("x: 0", Styles.LABEL_DEBUG);
        y = new Label("y: 0", Styles.LABEL_DEBUG);
        z = new Label("z: 0", Styles.LABEL_DEBUG);

        yaw = new Label("y: 0", Styles.LABEL_DEBUG);
        pitch = new Label("p: 0", Styles.LABEL_DEBUG);
        roll = new Label("r: 0", Styles.LABEL_DEBUG);

        fps = new Label("0 fps", Styles.LABEL_DEBUG);
        left().pad(20);
        left().top().add(fps).left().row();
        left().top().add(x).left().row();
        left().top().add(y).left().row();
        left().top().add(z).left().row();
        left().top().add(yaw).left().row();
        left().top().add(pitch).left().row();
        left().top().add(roll).left().row();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        x.setText("x: " + ship.getPosition().x);
        y.setText("y: " + ship.getPosition().y);
        z.setText("z: " + ship.getPosition().z);
        yaw.setText("y: " + ship.getOrientation().getYaw());
        pitch.setText("p: " + ship.getOrientation().getPitch());
        roll.setText("r: " + ship.getOrientation().getRoll());
        fps.setText(Gdx.graphics.getFramesPerSecond() + "fps");
        if (Gdx.graphics.getFramesPerSecond() > 40) {
            fps.setColor(Color.CYAN);
        } else if (Gdx.graphics.getFramesPerSecond() > 25) {
            fps.setColor(Color.LIME);
        } else {
            fps.setColor(Color.RED);
        }
    }
}
