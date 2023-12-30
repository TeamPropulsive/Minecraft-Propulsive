package io.github.teampropulsive.propulsive.type;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.Mesh;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;

import java.util.EnumMap;
import java.util.Vector;

public class SpaceObject {
    protected EnumMap<Direction, BakedQuad> planetQuads = new EnumMap<>(Direction.class);
    public float mass;
    public float size;
    public Vector3f position;
    public Vector3f velocity;
    public Identifier texture;
    public SpaceObject(Vector3f position, Vector3f velocity, float size, Identifier texture) {
        this.position = position;
        this.velocity = velocity;
        this.size = size;
        this.texture = texture;
    }
    // Calculates the force between 2 objects
    public float CalculateGravity(SpaceObject object) {
        return (float) (6.674 * (this.mass * object.mass) / (this.position.distanceSquared(object.position)));
    }
    // Applies a force to the given object
    public void ApplyForce(Vector3f force) {
        this.position = this.position.add(force);
    }
    @Environment(EnvType.CLIENT)
    protected void buildPlanetQuads() {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();
        SpriteIdentifier spriteId = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, this.texture);
        Sprite sprite = spriteId.getSprite();
        for (Direction direction : Direction.values()) {
            emitter.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            emitter.spriteColor(0, -1, -1, -1, -1);
            emitter.emit();
            Mesh mesh = builder.build();
            mesh.forEach(quadView -> this.planetQuads.put(direction, quadView.toBakedQuad(sprite)));
        }
    }

    public void render() {
        WorldRenderEvents.START.register(context -> {
            if (this.planetQuads.isEmpty())
                this.buildPlanetQuads();
            MinecraftClient client = MinecraftClient.getInstance();
            assert client.player != null;
            double distance = client.player.getPos().toVector3f().sub(this.position).lengthSquared();


            Camera camera = context.camera();
            Vector3f transformedPosition = this.position.sub(camera.getPos().toVector3f());

            float planetSize = (float) this.size;


            MatrixStack matrices = context.matrixStack();
            matrices.push();
            matrices.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);
/*


            for (Direction direction : Direction.values()) {
                matrices.push();
                Vec3i vec = direction.getVector();
                matrices.scale(planetSize, planetSize, planetSize);
                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.planetRot[1]));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(this.planetRot[0]));
                matrices.translate(vec.getX(), vec.getY(), vec.getZ());
                context.consumers().getBuffer(RenderLayer.getSolid()).quad(matrices.peek(), this.planetQuads.get(direction), 1.0f, 1.0f, 1.0f, 9999, OverlayTexture.DEFAULT_UV);
                matrices.pop();
            }
             */

            matrices.pop();

        });
    }

}
