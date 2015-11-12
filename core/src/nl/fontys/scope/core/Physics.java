package nl.fontys.scope.core;

public class Physics {

    public void apply(nl.fontys.scope.object.GameObject object, float delta) {

        object.getPosition().x += object.getVelocity().x * delta;
        object.getPosition().y += object.getVelocity().y * delta;
        object.getPosition().z += object.getVelocity().z * delta;

        object.getVelocity().scl(0.98f);
    }
}
