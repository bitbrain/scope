package nl.fontys.scope.controls;

public interface Moveable {

    void rotate(float yaw, float pitch, float roll);

    void rise(float factor);

    void boost(float strength);

    void shoot();
}
