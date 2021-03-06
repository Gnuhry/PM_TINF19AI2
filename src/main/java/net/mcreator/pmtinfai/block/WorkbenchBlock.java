
package net.mcreator.pmtinfai.block;

import io.netty.buffer.Unpooled;
import net.mcreator.pmtinfai.MKLGBlock;
import net.mcreator.pmtinfai.PMTINFAIElements;
import net.mcreator.pmtinfai.gui.WorkbenchGui;
import net.mcreator.pmtinfai.itemgroup.LogicBlocksItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.registries.ObjectHolder;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@PMTINFAIElements.ModElement.Tag
public class WorkbenchBlock extends PMTINFAIElements.ModElement {
    @ObjectHolder("pmtinfai:workbench")
    public static final Block block = null;
    @ObjectHolder("pmtinfai:workbench")
    public static final TileEntityType<CustomTileEntity> tileEntityType = null;

    public WorkbenchBlock(PMTINFAIElements instance) {
        super(instance, 16);
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @Override
    public void initElements() {
        elements.blocks.add(CustomBlock::new);
        elements.items
                .add(() -> new BlockItem(block, new Item.Properties().group(LogicBlocksItemGroup.tab)).setRegistryName(block.getRegistryName()));
    }

    @SubscribeEvent
    public void registerTileEntity(RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().register(TileEntityType.Builder.create(CustomTileEntity::new, block).build(null).setRegistryName("workbench"));
    }

    public static class CustomBlock extends Block {

        protected static final VoxelShape SWL_CORNER = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 2.0D, 14.0D, 2.0D);
        protected static final VoxelShape NWL_CORNER = Block.makeCuboidShape(0.0D, 0.0D, 14.0D, 2.0D, 14.0D, 16.0D);
        protected static final VoxelShape NEL_CORNER = Block.makeCuboidShape(14.0D, 0.0D, 14.0D, 16.0D, 14.0D, 16.0D);
        protected static final VoxelShape SEL_CORNER = Block.makeCuboidShape(14.0D, 0.0D, 0.0D, 16.0D, 14.0D, 2.0D);
        protected static final VoxelShape PLATE = Block.makeCuboidShape(0.0D, 14.0D, 0.0D, 16.0D, 14.5D, 16.0D);
        protected static final VoxelShape COMPLETE = VoxelShapes.or(SWL_CORNER, NWL_CORNER, NEL_CORNER, SEL_CORNER, PLATE);

        public CustomBlock() {
            super(Block.Properties.create(Material.WOOD).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD).lightValue(0));
            setRegistryName("workbench");
            MKLGBlock.Workbench=this;
        }

        public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
            return COMPLETE;
        }

        @Override
        public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
            List<ItemStack> dropsOriginal = super.getDrops(state, builder);
            if (!dropsOriginal.isEmpty())
                return dropsOriginal;
            return Collections.singletonList(new ItemStack(this, 1));
        }

        @Override
        public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity entity, Hand hand, BlockRayTraceResult hit) {
            boolean retval = super.onBlockActivated(state, world, pos, entity, hand, hit);
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            if (entity instanceof ServerPlayerEntity) {
                NetworkHooks.openGui((ServerPlayerEntity) entity, new INamedContainerProvider() {
                    @Override
                    public ITextComponent getDisplayName() {
                        return new StringTextComponent("Workbench");
                    }

                    @Override
                    public Container createMenu(int id, PlayerInventory inventory, PlayerEntity player) {
                        return new WorkbenchGui.GuiContainerMod(id, inventory, new PacketBuffer(Unpooled.buffer()).writeBlockPos(new BlockPos(x, y, z)));
                    }
                }, new BlockPos(x, y, z));
            }
            return true;
        }

        @Override
        public void onBlockHarvested(World worldIn, BlockPos pos, BlockState state, PlayerEntity player) {
            super.onBlockHarvested(worldIn, pos, state, player);
            CustomTileEntity cte = ((CustomTileEntity) worldIn.getTileEntity(pos));
            assert cte != null;
            cte.setInventorySlotContents(4, ItemStack.EMPTY);
            cte.setInventorySlotContents(5, ItemStack.EMPTY);
            cte.setInventorySlotContents(6, ItemStack.EMPTY);
            cte.setInventorySlotContents(7, ItemStack.EMPTY);
            cte.setInventorySlotContents(10, ItemStack.EMPTY);

        }

        @Override
        public INamedContainerProvider getContainer(BlockState state, World worldIn, BlockPos pos) {
            TileEntity tileEntity = worldIn.getTileEntity(pos);
            return tileEntity instanceof INamedContainerProvider ? (INamedContainerProvider) tileEntity : null;
        }

        @Override
        public boolean hasTileEntity(BlockState state) {
            return true;
        }

        @Override
        public TileEntity createTileEntity(BlockState state, IBlockReader world) {
            return new CustomTileEntity();
        }

        @Override
        public boolean eventReceived(BlockState state, World world, BlockPos pos, int eventID, int eventParam) {
            super.eventReceived(state, world, pos, eventID, eventParam);
            TileEntity tileentity = world.getTileEntity(pos);
            return tileentity != null && tileentity.receiveClientEvent(eventID, eventParam);
        }
        @Override
        public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
            if (!state.isValidPosition(worldIn, pos)) {
                TileEntity tileentity = state.hasTileEntity() ? worldIn.getTileEntity(pos) : null;
                spawnDrops(state, worldIn, pos, tileentity);
                worldIn.removeBlock(pos, false);
                for (Direction d : Direction.values())
                    worldIn.notifyNeighborsOfStateChange(pos.offset(d), this);
                return;
            }
        }

        /**
         * Abfrage ob es eine valide Position für den Block ist
         *
         * @param state   Blockstate des Blockes
         * @param worldIn Teil der Welt des Blockes
         * @param pos     Position des Blockes
         * @return Gibt an ob der Platz des Blockes valide ist
         */
        public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
            return func_220064_c(worldIn, pos.down());
        }
        @Override
        public void onReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean isMoving) {
            if (state.getBlock() != newState.getBlock()) {
                TileEntity tileentity = world.getTileEntity(pos);
                if (tileentity instanceof CustomTileEntity) {
                    InventoryHelper.dropInventoryItems(world, pos, (CustomTileEntity) tileentity);
                    world.updateComparatorOutputLevel(pos, this);
                }
                super.onReplaced(state, world, pos, newState, isMoving);
            }
        }
    }

    public static class CustomTileEntity extends LockableLootTileEntity {
        private NonNullList<ItemStack> stacks = NonNullList.withSize(11, ItemStack.EMPTY);
        private int kind = 28;

        protected CustomTileEntity() {
            super(Objects.requireNonNull(tileEntityType));
        }

        @Override
        public void read(CompoundNBT compound) {
            super.read(compound);
            this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
            kind = compound.getInt("kind");
            ItemStackHelper.loadAllItems(compound, this.stacks);
        }

        @Override
        public CompoundNBT write(CompoundNBT compound) {
            super.write(compound);
            compound.putInt("kind", kind);
            ItemStackHelper.saveAllItems(compound, this.stacks);
            return compound;
        }

        public int getKind() {
            return kind;
        }

        public void setKind(int kind) {
            this.kind = kind;
        }


        @Override
        public SUpdateTileEntityPacket getUpdatePacket() {
            return new SUpdateTileEntityPacket(this.pos, 0, this.getUpdateTag());
        }

        @Override
        public CompoundNBT getUpdateTag() {
            return this.write(new CompoundNBT());
        }

        @Override
        public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
            this.read(pkt.getNbtCompound());
        }

        @Override
        public int getSizeInventory() {
            return 11;
        }

        @Override
        public boolean isEmpty() {
            for (ItemStack itemstack : this.stacks)
                if (!itemstack.isEmpty())
                    return false;
            return true;
        }

        @Override
        public boolean isItemValidForSlot(int index, ItemStack stack) {
            return true;
        }

        @Override
        public ItemStack getStackInSlot(int slot) {
            return stacks.get(slot);
        }

        @Override
        public ITextComponent getDefaultName() {
            return new StringTextComponent("workbench");
        }

        @Override
        public int getInventoryStackLimit() {
            return 64;
        }

        @Override
        public Container createMenu(int id, PlayerInventory player) {
            return new WorkbenchGui.GuiContainerMod(id, player, new PacketBuffer(Unpooled.buffer()).writeBlockPos(this.getPos()));
        }

        @Override
        public ITextComponent getDisplayName() {
            return new StringTextComponent("Workbench");
        }

        @Override
        protected NonNullList<ItemStack> getItems() {
            return this.stacks;
        }

        @Override
        protected void setItems(NonNullList<ItemStack> stacks) {
            this.stacks = stacks;
        }
    }
}
