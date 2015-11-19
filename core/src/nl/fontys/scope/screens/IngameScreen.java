package nl.fontys.scope.screens;

import java.security.SecureRandom;
import java.util.UUID;

import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.controls.KeyboardControls;
import nl.fontys.scope.core.controller.CameraController;
import nl.fontys.scope.core.controller.ShipController;
import nl.fontys.scope.object.GameObject;

public class IngameScreen extends AbstractScreen {

    private KeyboardControls keyboardControls;

    private CameraController camController;

    @Override
    protected void onShow() {
        soundManager.play(Assets.Musics.STARSURFER, true);

        GameObject ship = factory.createShip(0f, 0f, 0f);
        ShipController controller = new ShipController();
        world.addController(ship, controller);
        camController = new CameraController(world.getCamera());
        world.addController(ship, camController);

        keyboardControls = new KeyboardControls(controller);

        SecureRandom random = new SecureRandom(UUID.randomUUID().toString().getBytes());
        factory.createEnergy(random.nextFloat() * 50f - 50f, random.nextFloat() * 50f - 50f, random.nextFloat() * 50f - 50f);
        factory.createPlanet(500f, 50f, 34f, 1.6f);
        factory.createPlanet(1000f, 80f, 234f, 1.2f);
        factory.createPlanet(1500f, 20f, 44f, 2.7f);
        factory.createPlanet(700f, 10f, 44f, 4.7f);
    }

    @Override
    protected void onUpdate(float delta) {
        keyboardControls.update(delta);
    }
}
