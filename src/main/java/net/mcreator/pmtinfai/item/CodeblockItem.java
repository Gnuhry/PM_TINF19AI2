
package net.mcreator.pmtinfai.item;

import net.mcreator.pmtinfai.MKLGItems;
import net.minecraftforge.registries.ObjectHolder;

import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;
import net.minecraft.block.BlockState;

import net.mcreator.pmtinfai.itemgroup.LogicBlocksItemGroup;
import net.mcreator.pmtinfai.PMTINFAIElements;

@PMTINFAIElements.ModElement.Tag
public class CodeblockItem extends PMTINFAIElements.ModElement {
	@ObjectHolder("pmtinfai:codeblock")
	public static final Item block = null;
	public CodeblockItem(PMTINFAIElements instance) {
		super(instance, 46);
	}

	@Override
	public void initElements() {
		elements.items.add(() -> new ItemCustom());
	}
	public static class ItemCustom extends Item {
		public ItemCustom() {
			super(new Item.Properties().group(LogicBlocksItemGroup.tab).maxStackSize(1));
			setRegistryName("codeblock");
			MKLGItems.CodeBenchItem=this;
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
