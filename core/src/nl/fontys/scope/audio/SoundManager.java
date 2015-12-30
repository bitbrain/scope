package nl.fontys.scope.audio;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;

import net.engio.mbassy.listener.Handler;

import java.util.HashMap;
import java.util.Map;

import nl.fontys.scope.assets.AssetManager;
import nl.fontys.scope.assets.Assets;
import nl.fontys.scope.core.controller.GameObjectController;
import nl.fontys.scope.event.EventType;
import nl.fontys.scope.event.Events;
import nl.fontys.scope.object.GameObject;

public final class SoundManager implements GameObjectController {

    private static final float MAX_PAN_DISTANCE = 50f;

    private static final float MAX_VOLUME_DISTANCE = 100f;

    private class SoundData {

        public Sound source;
        public long id;
        public boolean looping;

        public SoundData(Sound source, long id, boolean looping) {
            this.source = source;
            this.id = id;
            this.looping = looping;
        }
    }

    private Vector3 tmp = new Vector3();

    private static final SoundManager INSTANCE = new SoundManager();

    private Map<GameObject, SoundData> targets = new HashMap<GameObject, SoundData>();

    private Events events = Events.getInstance();

    private PerspectiveCamera camera;

    private SoundManager() {
        events.register(this);
    }

    public static SoundManager getInstance() {
        return INSTANCE;
    }

    public void setPerspectiveCamera(PerspectiveCamera camera) {
        this.camera = camera;
    }

    public long play(Assets.Sounds sounds, float volume, float pitch, float pan) {
        Sound sound = AssetManager.getSound(sounds);
        return sound.play(volume, pitch, pan);
    }

    public Music play(Assets.Musics musics, boolean loop) {
        Music music = AssetManager.getMusic(musics);
        music.setLooping(loop);
        music.play();
        return music;
    }

    public void play(GameObject target, Assets.Sounds sounds, boolean loop) {
        removeTarget(target);
        Sound sound = AssetManager.getSound(sounds);
        final float volume = calculateVolume(target.getPosition());
        final float pan = calculatePanning(target.getPosition());
        final long id = sound.play(volume, 1f, pan);
        sound.setLooping(id, loop);
        targets.put(target, new SoundData(sound, id, loop));
    }

    @Override
    public void update(GameObject object, float delta) {
        SoundData data = targets.get(object);
        if (data != null) {
            final float volume = calculateVolume(object.getPosition());
            final float pan = calculatePanning(object.getPosition());
            data.source.setPan(data.id, pan, volume);
        }
    }

    @Override
    public void update(GameObject object, GameObject other, float delta) {
        // noOp
    }

    @Handler
    public void onEvent(Events.GdxEvent event) {
        if (event.isTypeOf(EventType.OBJECT_REMOVED)) {
            GameObject object = (GameObject) event.getPrimaryParam();

        }
    }

    private void removeTarget(GameObject target) {
        SoundData data = targets.get(target);
        if (data != null) {
            data.source.setLooping(data.id, false);
            targets.remove(target);
        }
    }

    private float calculatePanning(Vector3 position) {
        if (camera != null) {
            tmp.set(camera.up);
            tmp.crs(camera.direction);
            tmp.nor();
            final float lenX = camera.position.x - position.x;
            final float lenY = camera.position.y - position.y;
            final float lenZ = camera.position.z - position.z;
            final float clampX = tmp.dot(new Vector3(lenX, lenY, lenZ));
            return MathUtils.clamp(clampX / MAX_PAN_DISTANCE, -1f, 1f);
        } else {
            return 0f;
        }
    }

    private float calculateVolume(Vector3 position) {
        tmp.set(camera.position.x, camera.position.y, camera.position.z);
        tmp.sub(position);
        return MathUtils.clamp(0.1f / (tmp.len() / MAX_VOLUME_DISTANCE), 0f, 1f);
    }
}
