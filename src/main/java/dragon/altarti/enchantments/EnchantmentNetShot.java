package dragon.altarti.enchantments;

import dragon.altarti.EnchantmentInit;
import dragon.altarti.util.Reference;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnchantmentNetShot extends Enchantment {

    public EnchantmentNetShot() {
        super(Rarity.VERY_RARE, EnumEnchantmentType.BOW, new EntityEquipmentSlot[] {EntityEquipmentSlot.MAINHAND});
        this.setName("netshot");
        this.setRegistryName(new ResourceLocation(Reference.MODID + ":netshot"));

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
        World world = user.getEntityWorld();
        BlockPos position = target.getPosition();

        if (world.getBlockState(position) == Blocks.AIR.getDefaultState() || world.getBlockState(position) == Blocks.TALLGRASS.getPlant(world, position)) {
            world.setBlockState(position, Blocks.WEB.getDefaultState());
        }
        if (world.getBlockState(new BlockPos(position.getX() + 1, position.getY(), position.getZ())) == Blocks.AIR.getDefaultState() || world.getBlockState(new BlockPos(position.getX() + 1, position.getY(), position.getZ())) == Blocks.TALLGRASS.getPlant(world, new BlockPos(position.getX() + 1, position.getY(), position.getZ()))) {
            world.setBlockState(new BlockPos(position.getX() + 1, position.getY(), position.getZ()), Blocks.WEB.getDefaultState());
        }
        if (world.getBlockState(new BlockPos(position.getX() + 1, position.getY(), position.getZ() + 1)) == Blocks.AIR.getDefaultState() || world.getBlockState(new BlockPos(position.getX() + 1, position.getY(), position.getZ() + 1)) == Blocks.TALLGRASS.getPlant(world, new BlockPos(position.getX() + 1, position.getY(), position.getZ() + 1))) {
            world.setBlockState(new BlockPos(position.getX() + 1, position.getY(), position.getZ() + 1), Blocks.WEB.getDefaultState());
        }
        if (world.getBlockState(new BlockPos(position.getX() + 1, position.getY(), position.getZ() - 1)) == Blocks.AIR.getDefaultState() || world.getBlockState(new BlockPos(position.getX() + 1, position.getY(), position.getZ() - 1)) == Blocks.TALLGRASS.getPlant(world, new BlockPos(position.getX() + 1, position.getY(), position.getZ() - 1))) {
            world.setBlockState(new BlockPos(position.getX() + 1, position.getY(), position.getZ() - 1), Blocks.WEB.getDefaultState());
        }
        if (world.getBlockState(new BlockPos(position.getX() - 1, position.getY(), position.getZ() + 1)) == Blocks.AIR.getDefaultState() || world.getBlockState(new BlockPos(position.getX() - 1, position.getY(), position.getZ() + 1)) == Blocks.TALLGRASS.getPlant(world, new BlockPos(position.getX() - 1, position.getY(), position.getZ() + 1))) {
            world.setBlockState(new BlockPos(position.getX() - 1, position.getY(), position.getZ() + 1), Blocks.WEB.getDefaultState());
        }
        if (world.getBlockState(new BlockPos(position.getX() - 1, position.getY(), position.getZ())) == Blocks.AIR.getDefaultState() || world.getBlockState(new BlockPos(position.getX() - 1, position.getY(), position.getZ())) == Blocks.TALLGRASS.getPlant(world, new BlockPos(position.getX() - 1, position.getY(), position.getZ()))) {
            world.setBlockState(new BlockPos(position.getX() - 1, position.getY(), position.getZ()), Blocks.WEB.getDefaultState());
        }
        if (world.getBlockState(new BlockPos(position.getX() - 1, position.getY(), position.getZ() - 1)) == Blocks.AIR.getDefaultState() || world.getBlockState(new BlockPos(position.getX() - 1, position.getY(), position.getZ() - 1)) == Blocks.TALLGRASS.getPlant(world, new BlockPos(position.getX() - 1, position.getY(), position.getZ() - 1))) {
            world.setBlockState(new BlockPos(position.getX() - 1, position.getY(), position.getZ() - 1), Blocks.WEB.getDefaultState());
        }
        if (world.getBlockState(new BlockPos(position.getX(), position.getY(), position.getZ() + 1)) == Blocks.AIR.getDefaultState() || world.getBlockState(new BlockPos(position.getX(), position.getY(), position.getZ() + 1)) == Blocks.TALLGRASS.getPlant(world, new BlockPos(position.getX(), position.getY(), position.getZ() + 1))) {
            world.setBlockState(new BlockPos(position.getX(), position.getY(), position.getZ() + 1), Blocks.WEB.getDefaultState());
        }
        if (world.getBlockState(new BlockPos(position.getX(), position.getY(), position.getZ() - 1)) == Blocks.AIR.getDefaultState() || world.getBlockState(new BlockPos(position.getX(), position.getY(), position.getZ() - 1)) == Blocks.TALLGRASS.getPlant(world, new BlockPos(position.getX(), position.getY(), position.getZ() - 1))) {
            world.setBlockState(new BlockPos(position.getX(), position.getY(), position.getZ() - 1), Blocks.WEB.getDefaultState());
        }
    }

}