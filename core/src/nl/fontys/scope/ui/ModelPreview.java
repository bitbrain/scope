package nl.fontys.scope.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.UUID;

import nl.fontys.scope.graphics.TextureBacker;
import nl.fontys.scope.util.Colors;

public class ModelPreview extends Actor {

    private ModelInstance instance;

    private ModelBatch batch;

    private Environment environment;

    private PerspectiveCamera camera;

    private FrameBuffer buffer;

    private String handleId;

    private TextureBacker backer;

    public ModelPreview(TextureBacker textureBacker, Model model, float width, float height) {
        this.handleId = UUID.randomUUID().toString();
        textureBacker.register(handleId, new ModelBackeable());
        this.backer = textureBacker;
        this.instance = new ModelInstance(model);
        this.batch = new ModelBatch();
        this.environment = new Environment();
        this.camera = new PerspectiveCamera(40f, width, height);
        setupCamera();
        setupEnvironment();
        setSize(width, height);
    }

    @Override
    public void setSize(float width, float height) {
        super.setSize(width, height);
        revalidateFrameBuffer(width, height);
    }

    @Override
    public void setWidth(float width) {
        super.setWidth(width);
        revalidateFrameBuffer(width, getHeight());
    }

    @Override
    public void setHeight(float height) {
        super.setHeight(height);
        revalidateFrameBuffer(getWidth(), height);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        ActorShadow.draw(batch, this);
        batch.draw(backer.getBackedTexture(handleId), getX(), getY(), getWidth(), getHeight());
    }

    private void revalidateFrameBuffer(float width, float height) {
        if (buffer != null) {
            buffer.dispose();
        }
        buffer = new FrameBuffer(Pixmap.Format.RGBA4444, (int)width, (int)height, false);
        camera.viewportWidth = width;
        camera.viewportHeight = height;
    }

    private void setupCamera() {
        camera.position.set(4f, 4f, 4f);
        camera.near = 0.2f;
        camera.far = 30000f;
        camera.lookAt(0f, 0f, 0f);
        camera.up.set(0f, -1f, 0f);
    }

    private void setupEnvironment() {
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.2f, 0.6f, 1f));
        environment.add(new PointLight().set(Colors.PRIMARY, 5f, 5f, 5f, 25));
    }

    private class ModelBackeable implements TextureBacker.Backeable {

        @Override
        public Texture back(Batch batch, float alphaModulation) {
            camera.update();
            buffer.begin();
            Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
            Gdx.gl.glViewport(0, 0, (int) getWidth(), (int) getHeight());
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
            ModelPreview.this.batch.begin(camera);
            ModelPreview.this.batch.render(instance, environment);
            instance.transform.rotate(Vector3.Y, 2f);
            ModelPreview.this.batch.end();
            buffer.end();
            return buffer.getColorBufferTexture();
        }
    }
}
