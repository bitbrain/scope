package nl.fontys.scope.core;

import java.security.SecureRandom;
import java.util.UUID;

import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;
import nl.fontys.scope.util.Colors;

public class Arena {

    private static final int DEFAULT_ENERGY_COUNT =100;

    private GameObjectFactory factory;

    private SecureRandom random;

    public Arena(GameObjectFactory factory) {
        this.factory = factory;
        random = new SecureRandom(UUID.randomUUID().toString().getBytes());
    }

    public void setup() {
        GameObject sphere = factory.createSphere(5f);
        sphere.getColor().set(Colors.PRIMARY);
        sphere.getColor().a = 0.8f;
        for (int i = 0; i < DEFAULT_ENERGY_COUNT; ++i) {
            float angle = 360f * random.nextFloat();
            float radius = 40f + 100f * random.nextFloat();
            float x = (float)(Math.cos(Math.toRadians(angle)) * radius);
            float z = (float)(Math.sin(Math.toRadians(angle)) * radius);
            factory.createEnergy(x, 0f, z).getColor().set(Colors.PRIMARY);
        }
    }
}
