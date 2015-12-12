package nl.fontys.scope.object;

import com.badlogic.gdx.graphics.Color;
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

    private Color color = Color.WHITE.cpy();

    public void set(GameObject object) {
        this.position = object.position;
        this.lastPosition = object.lastPosition;
        this.orientation = object.orientation;
        this.velocity = object.velocity;
        this.scale = object.scale;
        this.id = object.id;
        this.type = object.type;
        this.color = object.color;
    }

    public float getScale() {
        return scale;
    }

    public Color getColor() { return color; }

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
        color = Color.WHITE.cpy();
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
