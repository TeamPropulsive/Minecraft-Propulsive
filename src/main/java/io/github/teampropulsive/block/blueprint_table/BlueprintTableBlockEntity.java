package io.github.teampropulsive.block.blueprint_table;

import io.github.teampropulsive.screen.BlueprintTableScreenHandler;
import io.github.teampropulsive.util.BlockBox;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static io.github.teampropulsive.block.Blocks.*;

public class BlueprintTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory {
    private static final int MAX_PAD_SIZE = 10;
    private static final int MAX_TOWER_HEIGHT = 10;

    public static final int PROPERTY_COUNT = 1;

    public boolean showBoundingBox = true;

    @Nullable
    private BlockBox constructionArea;

    // Used to sync ints between the server and the client GUI
    // https://fabricmc.net/wiki/tutorial:propertydelegates
    private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
        @Override
        public int get(int index) {
            if (index == 0)
                return showBoundingBox ? 1 : 0;
            return 0;
        }

        @Override
        public void set(int index, int value) {
            if (index == 0) {
                showBoundingBox = value == 1;
                world.updateListeners(pos, getCachedState(), getCachedState(), Block.NOTIFY_LISTENERS);
            }
            markDirty();
        }

        @Override
        public int size() {
            return PROPERTY_COUNT;
        }
    };

    public BlueprintTableBlockEntity(BlockPos pos, BlockState state) {
        super(BLUEPRINT_TABLE_BLOCK_ENTITY, pos, state);
    }

    // Checks if a given square is a valid pad base
    private static boolean checkLaunchpad(int size, World world, BlockPos pos) {
        for (int x = -size; x <= size; x++) {
            for (int z = -size; z <= size; z++) {
                if (!(world.getBlockState(pos.add(x, 0, z)).getBlock() == LAUNCH_PAD))
                    return false;
            }
        }
        return true;
    }

    private static Optional<Direction> getPadDirection(World world, BlockPos tablePos) {
        BlockPos below = tablePos.down();
        for (int i = 0; i < 4; i++) {
            Direction direction = Direction.fromHorizontal(i);
            if (world.getBlockState(below.offset(direction)).isOf(LAUNCH_PAD))
                return Optional.of(direction);
        }

        return Optional.empty();
    }

    private static Optional<BlockBox> getPadArea(World world, BlockPos tablePos, Direction padDirection) {
        BlockPos below = tablePos.down().offset(padDirection);
        for (int size = MAX_PAD_SIZE; size > 0; size--) {
            BlockPos center = below.offset(padDirection, size);
            boolean padFound = checkLaunchpad(size, world, center);
            if (padFound)
                return Optional.of(new BlockBox(center).expand(size, 0, size));
        }

        return Optional.empty();
    }

    private static Optional<BlockPos> getTowerBase(World world, BlockBox padArea) {
        BlockBox searchArea = padArea.expand(1);
        for (int x = searchArea.minX; x <= searchArea.maxX; x++) {
            for (int z = searchArea.minZ; z <= searchArea.maxZ; z++) {
                BlockPos towerBase = new BlockPos(x, padArea.center().getY() + 1, z);
                if (world.getBlockState(towerBase).isOf(LAUNCH_TOWER))
                    return Optional.of(towerBase);
            }
        }

        return Optional.empty();
    }

    private static int getTowerHeight(World world, BlockPos towerBase) {
        for (int y = 1; y <= MAX_TOWER_HEIGHT; y++) {
            if (!world.getBlockState(towerBase.up(y)).isOf(LAUNCH_TOWER))
                return y - 1;
        }
        return MAX_TOWER_HEIGHT;
    }

    private static Optional<BlockBox> findConstructionArea(World world, BlockPos tablePos) {
        return getPadDirection(world, tablePos)
            .flatMap(direction -> getPadArea(world, tablePos, direction))
            .flatMap(padArea -> getTowerBase(world, padArea).map(towerBase -> {
                int towerHeight = getTowerHeight(world, towerBase);
                return padArea.up().extend(0, towerHeight, 0);
            }));
    }

    public static void tick(World world, BlockPos pos, BlockState state, BlueprintTableBlockEntity be) {
        be.checkLaunchpad();
    }

    public void checkLaunchpad() {
        this.constructionArea = findConstructionArea(this.world, this.pos).orElse(null);
    }

    public Optional<BlockBox> getConstructionArea() {
        return Optional.ofNullable(this.constructionArea);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        nbt.putBoolean("showOutline", this.showBoundingBox);

        super.writeNbt(nbt);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);

        this.showBoundingBox = nbt.getBoolean("showOutline");
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable(this.getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return new BlueprintTableScreenHandler(syncId, this.propertyDelegate);
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBoolean(this.showBoundingBox);
    }
}

