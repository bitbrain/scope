package nl.fontys.scope.object;

import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

import java.util.UUID;

/**
 * Basic game object
 */
public class GameObject implements Pool.Poolable {

    private Vector3 position = new Vector3();

    private Vector3 lastPosition = new Vector3();

    private Quaternion orientation = new Quaternion();

    private Vector3 velocity = new Vector3();

    private float scale = 1f;

    private String id = UUID.randomUUID().toString();

    private GameObjectType type = GameObjectType.NONE;

    public float getScale() {
        return scale;
    }

    public GameObjectType getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public Quaternion getOrientation() {
        return orientation;
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setOrientation(float x, float y, float z, float w) {
        this.orientation.set(x, y, z, w);
    }

    public void setPosition(float x, float y, float z) {
        lastPosition.set(this.position.x, this.position.y, this.position.z);
        this.position.set(x, y, z);
    }

    public boolean hasMoved() {
        return !lastPosition.equals(position);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setType(GameObjectType type) {
        this.type = type;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setVelocity(Vector3 velocity) {
        this.velocity = velocity;
    }

    @Override
    public void reset() {
        lastPosition.set(0f, 0f, 0f);
        position.set(0f, 0f, 0f);
        scale = 1f;
        orientation.set(0f, 0f, 0f, 0f);
        velocity.set(0f, 0f, 0f);
        type = GameObjectType.NONE;
    }
}
