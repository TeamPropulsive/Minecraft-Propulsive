package com.june.propulsive.types;

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

import java.util.EnumMap;
import java.util.List;

import static com.june.propulsive.Propulsive.PLANET_3D_RENDER_DIST;
import static com.june.propulsive.Propulsive.SPACE;
public abstract class Planet {
    public double planetSize;
    public Identifier texture2d;
    public Identifier texture3d;
    public float[] planetRot = { 0.0f, 0.0f };
    public Vec3d planetPos;
    boolean is3D = true;
    protected EnumMap<Direction, BakedQuad> planetQuads = new EnumMap<>(Direction.class);

    public Planet(double scale, double posX, double posY, double posZ, float horizontalRotation, float verticalRotation, Identifier texture2d, Identifier texture3d) {
        // Will add more args in the future (Link a dimension, textures, etc)
        this.planetSize = scale;
        this.planetPos = new Vec3d(posX, posY, posZ);
        this.texture2d = texture2d;
        this.texture3d = texture3d;
        this.planetRot[0] = horizontalRotation;
        this.planetRot[1] = verticalRotation;
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
            emitter.spriteBake(0, sprite, MutableQuadView.BAKE_LOCK_UV);
            emitter.spriteColor(0, -1, -1, -1, -1);
            emitter.emit();
            Mesh mesh = builder.build();
            mesh.forEach(quadView -> this.planetQuads.put(direction, quadView.toBakedQuad(sprite)));
        }
    }

    // Assuming all worlds are 60,000,000 blocks for now
    private static final float PLANET_DIM_RADIUS = 30_000_000f;
    private static final float FRONT_PERCENT = 0.3f;
    private static final float BACK_PERCENT = 0.2f;
    public Direction xzToSide(double x, double z) {
        if (Math.abs(x) > PLANET_DIM_RADIUS * (1-BACK_PERCENT) || Math.abs(z) > PLANET_DIM_RADIUS * (1-BACK_PERCENT)) {
            return Direction.DOWN;
        } else if (Math.abs(x) < PLANET_DIM_RADIUS * FRONT_PERCENT && Math.abs(z) < PLANET_DIM_RADIUS * FRONT_PERCENT) {
            return Direction.UP;
        } else if (z >= Math.abs(x)) {
            return Direction.NORTH;
        } else if (x >= Math.abs(z)) {
            return Direction.EAST;
        } else if (z < x) {
            return Direction.SOUTH;
        } else {
            return Direction.WEST;
        }
    }

    // Renders planet in space
    public void render() {
        WorldRenderEvents.START.register(context -> {
            if (context.world().getRegistryKey() == SPACE) {
                if (this.planetQuads.isEmpty())
                    this.buildPlanetQuads();
                MinecraftClient client = MinecraftClient.getInstance();
                assert client.player != null;
                double distance = client.player.getPos().subtract(this.planetPos).lengthSquared();
                if (this.is3D && distance > PLANET_3D_RENDER_DIST*PLANET_3D_RENDER_DIST) this.is3D = false;
                else if (!this.is3D && distance < PLANET_3D_RENDER_DIST*PLANET_3D_RENDER_DIST) this.is3D = true;

                Camera camera = context.camera();
                Vec3d transformedPosition = this.planetPos.subtract(camera.getPos());

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
        List<ServerPlayerEntity> players = server.getPlayerManager().getPlayerList();
        for (ServerPlayerEntity player : players) {
            if (player.getWorld().getRegistryKey() == SPACE) {
                double distance = player.getPos().subtract(this.planetPos).length();
                if (distance < (this.planetSize * 2.01)) {
                    collisionDetected(player);
                }
            }

        }
    };

    public abstract void collisionDetected(ServerPlayerEntity player);




    // x, y need to be between 0-1, divide by the size
    public static double[] planeToCube(double x, double y) {
        double normalizedX = (x - 0.5) * 2.0;
        double normalizedY = (y - 0.5) * 2.0;
        int face;
        double u, v;

        if (Math.abs(normalizedX) > Math.abs(normalizedY)) {
            if (normalizedX > 0.0) {
                // Right face
                face = 0;
                u = normalizedY / normalizedX;
                v = 0.5;
            } else {
                // Left face
                face = 1;
                u = -normalizedY / normalizedX;
                v = 0.5;
            }
        } else {
            if (normalizedY > 0.0) face = 2;
            else face = 3;
            u = normalizedX / normalizedY;
            v = 0.5;
        }
        double z = 1.0;
        double[] cubeCoordinates = new double[3];
        switch (face) {
            case 0 -> {
                cubeCoordinates[0] = z;
                cubeCoordinates[1] = u;
                cubeCoordinates[2] = v;
            }
            case 1 -> {
                cubeCoordinates[0] = -z;
                cubeCoordinates[1] = u;
                cubeCoordinates[2] = v;
            }
            case 2 -> {
                cubeCoordinates[0] = u;
                cubeCoordinates[1] = z;
                cubeCoordinates[2] = v;
            }
            case 3 -> {
                cubeCoordinates[0] = u;
                cubeCoordinates[1] = v;
                cubeCoordinates[2] = -z;
            }
            default -> System.err.println("oh god");
        }
        return cubeCoordinates;
    }
    public static double[] cubeToPlane(double x, double y, double z) {
        int face = 0;
        if (Math.abs(x) > Math.abs(y) && Math.abs(x) > Math.abs(z)) {
            if (x > 0.0) {
                face = 0;
            } else {
                face = 1;
            }
        } else if (Math.abs(y) > Math.abs(x) && Math.abs(y) > Math.abs(z)) {
            if (y > 0.0) face = 2;
            else face = 3;
        } else System.err.println("oh god");
        double u, v;
        v = switch (face) {
            case 0 -> {
                u = y / z;
                yield 0.5;
            }
            case 1 -> {
                u = -y / z;
                yield 0.5;
            }
            case 2 -> {
                u = x / z;
                yield 0.5;
            }
            case 3 -> {
                u = x / y;
                yield 0.5;
            }
            default -> throw new IllegalStateException("Unexpected value: " + face);
        };
        double xFlat = (u / 2.0) + 0.5;
        double yFlat = (v / 2.0) + 0.5;
        return new double[] { xFlat, yFlat };
    }

}
