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
import net.minecraft.registry.RegistryKey;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Pair;

import java.util.EnumMap;
import java.util.List;

import static io.github.teampropulsive.Propulsive.*;

public abstract class Planet {

    public PlanetDimensions dimensions;
    public double planetSize;
    public Identifier texture2d;
    public Identifier texture3d;
    public float[] planetRot = { 0.0f, 0.0f };
    public Vec3d currentPos;
    public Vec3d startingPos;
    boolean is3D = true;
    public double orbitalPeriod = 1.0;
    public Planet parent = null;
    protected EnumMap<Direction, BakedQuad> planetQuads = new EnumMap<>(Direction.class);

    public Planet(double scale, double posX, double posY, double posZ, double orbitTime, float horizontalRotation, float verticalRotation, Identifier texture2d, Identifier texture3d, PlanetDimensions dimensions) {
        // Will add more args in the future (Link a dimension, textures, etc)
        this.planetSize = scale;
        this.orbitalPeriod = orbitTime;
        this.startingPos = new Vec3d(posX, posY, posZ);
        this.currentPos = new Vec3d(posX, posY, posZ);
        this.texture2d = texture2d;
        this.texture3d = texture3d;
        this.planetRot[0] = horizontalRotation;
        this.planetRot[1] = verticalRotation;
        this.dimensions = dimensions;
    }

    // TODO use an entity or something instead of individual quads
    @Environment(EnvType.CLIENT)
    protected void buildPlanetQuads() {
        Renderer renderer = RendererAccess.INSTANCE.getRenderer();
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

    public void getSpacePos(Vec3d playerpos, RegistryKey<World> world) {
        // TODO : Account for rotated planets
        // TODO : Finish impl
        double x = (playerpos.x / this.dimensions.faceRadius()) - this.dimensions.getOffset(world);
        double z = playerpos.z / this.dimensions.faceRadius();

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
                    matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(this.planetRot[1]));
                    matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(this.planetRot[0]));
                    matrices.translate(vec.getX(), vec.getY(), vec.getZ());
                    context.consumers().getBuffer(RenderLayer.getSolid()).quad(matrices.peek(), this.planetQuads.get(direction), 1.0f, 1.0f, 1.0f, 9999, OverlayTexture.DEFAULT_UV);
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
            this.currentPos = PlanetGravityHandler.currentPosition(this, this.parent, server.getWorld(SPACE).getTime());

    };

    public abstract void collisionDetected(ServerPlayerEntity player);

}
