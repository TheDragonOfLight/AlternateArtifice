package dragon.altarti.enchantments;

import dragon.altarti.EnchantmentInit;
import dragon.altarti.util.Reference;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

public class EnchantmentExecutioner extends Enchantment {
    public int executionerStacks;

    public EnchantmentExecutioner() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.WEAPON, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        this.setName("executioner");
        this.setRegistryName(new ResourceLocation(Reference.MODID + ":executioner"));

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

    @Override
    public void onEntityDamaged(EntityLivingBase user, Entity target, int level) {
        if (!target.isDead) {
            Random rand = new Random();
            if (rand.nextInt(4) == 0) {
                EntityLivingBase targetBase = (EntityLivingBase) target;
                float executionerDamage = ((targetBase.getMaxHealth() - targetBase.getHealth()) / 3);

                targetBase.setHealth(targetBase.getHealth() - executionerDamage);
            }
        }
    }
}
