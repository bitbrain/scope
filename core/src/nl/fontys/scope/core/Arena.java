package nl.fontys.scope.core;

import nl.fontys.scope.object.GameObject;
import nl.fontys.scope.object.GameObjectFactory;

public class Arena {

    private GameObjectFactory factory;

    public Arena(GameObjectFactory factory) {
        this.factory = factory;
    }

    public void setup() {
        GameObject sphere = factory.createPlanet(0f, 20f, 0f, 0f);
        sphere.getColor().a = 0.6f;
    }
}
