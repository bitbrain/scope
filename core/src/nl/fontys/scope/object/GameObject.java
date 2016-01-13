package nl.fontys.scope.object;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Pool;

import java.io.Serializable;
import java.util.UUID;

/**
 * Basic game object
 */
public class GameObject implements Pool.Poolable, Serializable {

    private Vector3 position;

    private Vector3 lastPosition;

    private float collisionScale;

    private Quaternion orientation;

    private Vector3 velocity;

    private float scale;

    private String id;

    private GameObjectType type;

    private Color color;

    private String externalId;

    private boolean physics;

    public void set(GameObject object) {
        this.position = object.position;
        this.lastPosition = object.lastPosition;
        this.orientation = object.orientation;
        this.velocity = object.velocity;
        this.scale = object.scale;
        this.id = object.id;
        this.type = object.type;
        this.color = object.color;
        this.physics = object.physics;
        this.collisionScale = object.collisionScale;
        this.externalId = object.externalId;
    }

    public float getScale() {
        return scale;
    }

    public Color getColor() { return color; }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public float getCollisionScale() {
        return collisionScale;
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

    public void setCollisionScale(float scale) {
        this.collisionScale = Math.abs(scale);
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

    public boolean hasPhysics() {
        return physics;
    }

    public void setPhysics(boolean physics) {
        this.physics = physics;
    }

    @Override
    public void reset() {
        if (position == null) {
            position = new Vector3();
        }
        if (lastPosition == null) {
            lastPosition = new Vector3();
        }
        if (velocity == null) {
            velocity = new Vector3();
        }
        if (orientation == null) {
            orientation = new Quaternion();
        }
        physics = true;
        lastPosition.set(0f, 0f, 0f);
        position.set(0f, 0f, 0f);
        scale = 1f;
        orientation.set(0f, 0f, 0f, 0f);
        velocity.set(0f, 0f, 0f);
        type = GameObjectType.NONE;
        color = Color.WHITE.cpy();
        collisionScale = 0f;
        id = UUID.randomUUID().toString();
        externalId = "";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameObject that = (GameObject) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return type == that.type;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }
}
