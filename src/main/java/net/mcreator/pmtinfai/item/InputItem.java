
package net.mcreator.pmtinfai.item;

import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.pmtinfai.itemgroup.LogicBlocksItemGroup;
import net.mcreator.pmtinfai.PMTINFAIElements;

@PMTINFAIElements.ModElement.Tag
public class InputItem extends PMTINFAIElements.ModElement {
	@ObjectHolder("pmtinfai:input")
	public static final Item block = null;
	public InputItem(PMTINFAIElements instance) {
		super(instance, 6);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(LogicBlocksItemGroup.tab).maxStackSize(16));
			setRegistryName("input");
		}

		@Override
		public int getItemEnchantability() {
			return 0;
		}

		@Override
		public int getUseDuration(ItemStack itemstack) {
			return 0;
		}

		@Override
		public float getDestroySpeed(ItemStack par1ItemStack, BlockState par2Block) {
			return 1F;
		}
	}
}