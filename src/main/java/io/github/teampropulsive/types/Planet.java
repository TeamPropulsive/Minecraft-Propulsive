package io.github.teampropulsive.types;

import io.github.teampropulsive.handler.PlanetGravityHandler;
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
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import qouteall.q_misc_util.my_util.Vec2d;

import java.util.EnumMap;
import java.util.List;
import java.util.Objects;

import static io.github.teampropulsive.Propulsive.*;

public abstract class Planet {
    public double planetSize;
    public Identifier texture2d;
    public Identifier texture3d;
    public Vec2d planetRot;
    public Vec3d currentPos;
    public Vec3d startingPos;
    boolean is3D = true;
    public double orbitalPeriod;
    public Planet parent = null;
    public double rotSpeed;
    public Vec2d rotAngle;
    protected EnumMap<Direction, BakedQuad> planetQuads = new EnumMap<>(Direction.class);

    public Planet(double scale, Vec3d pos, double orbitTime, double rotationTime, Vec2d rotationAngle, Identifier texture2d, Identifier texture3d) {
        this.planetSize = scale;
        this.orbitalPeriod = orbitTime;
        this.startingPos = pos;
        this.currentPos = pos;
        this.texture2d = texture2d;
        this.texture3d = texture3d;
        this.planetRot = rotationAngle;
        this.rotSpeed = rotationTime;
        this.rotAngle = rotationAngle;
    }



    // Renders planet in space
    public void render() {
        WorldRenderEvents.START.register(context -> {
            if (context.world().getRegistryKey() == SPACE) {
                if (this.planetQuads.isEmpty())
                    this.buildPlanetQuads();
                MinecraftClient client = MinecraftClient.getInstance();
                assert client.player != null;
                double distance = client.player.getPos().subtract(this.currentPos).lengthSquared();
                if (this.is3D && distance > PLANET_3D_RENDER_DIST*PLANET_3D_RENDER_DIST) this.is3D = false;
                else if (!this.is3D && distance < PLANET_3D_RENDER_DIST*PLANET_3D_RENDER_DIST) this.is3D = true;

                Camera camera = context.camera();
                Vec3d transformedPosition = this.currentPos.subtract(camera.getPos());

                float planetSize = (float) this.planetSize;


                MatrixStack matrices = context.matrixStack();
                matrices.push();
                matrices.translate(transformedPosition.x, transformedPosition.y, transformedPosition.z);

                for (Direction direction : Direction.values()) {
                    matrices.push();
                    Vec3i vec = direction.getVector();
                    matrices.scale(planetSize, planetSize, planetSize);
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) this.planetRot.x()));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) this.planetRot.y()));
                    matrices.translate(vec.getX(), vec.getY(), vec.getZ());
                    Objects.requireNonNull(context.consumers()).getBuffer(RenderLayer.getSolid()).quad(matrices.peek(), this.planetQuads.get(direction), 1.0f, 1.0f, 1.0f, 9999, OverlayTexture.DEFAULT_UV);
                    matrices.pop();
                }
                matrices.pop();
            }
        });
    }

    // Planet tick
    // *WARNING* Code here can have a large impact on performance! You have been warned!
    // This code is on the server, not the client
    public void tick(MinecraftServer server) {
        // Collision
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        for (ServerPlayerEntity player : players) {
            if (player.getWorld().getRegistryKey() == SPACE) {
                double distance = player.getPos().subtract(this.currentPos).length();
                if (distance < (this.planetSize * 2.01))
                    collisionDetected(player);
            }
        }
        // Gravity
        if (this.parent != null)
            this.currentPos = PlanetGravityHandler.currentPosition(this, this.parent, Objects.requireNonNull(server.getWorld(SPACE)).getTime());

        this.planetRot = new Vec2d(this.rotAngle.x() * this.rotSpeed * Objects.requireNonNull(server.getWorld(SPACE)).getTime(), this.rotAngle.y() * this.rotSpeed * Objects.requireNonNull(server.getWorld(SPACE)).getTime());

    };

    public abstract void collisionDetected(ServerPlayerEntity player);
    @Environment(EnvType.CLIENT)
    protected void buildPlanetQuads() {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
        assert renderer != null;
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();
        SpriteIdentifier spriteId = new SpriteIdentifier(PlayerScreenHandler.BLOCK_ATLAS_TEXTURE, this.texture3d);
        Sprite sprite = spriteId.getSprite();
        for (Direction direction : Direction.values()) {
            emitter.square(direction, 0.0f, 0.0f, 1.0f, 1.0f, 1.0f);
            emitter.spriteBake(sprite, MutableQuadView.BAKE_LOCK_UV);
            emitter.emit();
            Mesh mesh = builder.build();
            mesh.forEach(quadView -> this.planetQuads.put(direction, quadView.toBakedQuad(sprite)));
        }
    }
}
