package dragon.altarti.enchantments;

import dragon.altarti.EnchantmentInit;
import dragon.altarti.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class EnchantmentArcaneHeadlamp extends Enchantment {
    public EnchantmentArcaneHeadlamp() {
        super(Enchantment.Rarity.VERY_RARE, EnumEnchantmentType.ARMOR_HEAD, new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD});
        this.setName("arcaneheadlamp");
        this.setRegistryName(new ResourceLocation(Reference.MODID + ":arcaneheadlamp"));

        EnchantmentInit.ENCHANTMENTS.add(this);
    }

    @Override
    public int getMinEnchantability(int enchantmentLevel) {
        return 20 * 1;
    }

    @Override
    public int getMaxEnchantability(int enchantmentLevel) {
        return 20 * 1;
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    protected boolean canApplyTogether(Enchantment ench) {
        return super.canApplyTogether(ench);
    }

    @Override
    public boolean isTreasureEnchantment()
    {
        return false;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack)
    {
        return false;
    }
}