package dragon.altarti.artitable;

import dragon.altarti.AlternateArtifice;
import dragon.altarti.EnchantmentInit;
import dragon.altarti.items.Topaz;
import dragon.altarti.util.Reference;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static net.minecraft.enchantment.EnchantmentHelper.getEnchantments;

public class ArtificersEnchanter extends Block implements ITileEntityProvider {
    //Sets name of the mod for easy reference.
    public static final ResourceLocation ENCHANTERNAME = new ResourceLocation(Reference.MODID, "artitable");

    public ArtificersEnchanter() {
        //Provides info on how block will sound.
        super(Material.ROCK);

        //Creates registry for the furnace.
        setRegistryName(ENCHANTERNAME);
        //Name the player sees.
        setUnlocalizedName("artitable");
        //Tool and mining level needed to acquire block. (2 = Iron)
        setHarvestLevel("pickaxe", 2);
        //Creative tab the item will show up in.
        setCreativeTab(CreativeTabs.MISC);
        //Sets explosion resistance to enchantment table levels.
        setHardness(5);
        //Makes the block emit a warm glow.
        setLightLevel((float) (6/16.0));
    }

    //Renders the block in the user inventory.
    @SideOnly(Side.CLIENT)
    public void initModel() {

        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(this.getRegistryName(), "inventory"));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileArtificersEnchanter();
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (world.isRemote) {
            return true;
        }
        TileEntity te = world.getTileEntity(pos);
        if (!(te instanceof TileArtificersEnchanter)) {
            return false;
        }
        if (player.isSneaking()) {
            player.openGui(AlternateArtifice.instance, 0, world, pos.getX(), pos.getY(), pos.getZ());
        } else {
            ItemStack topazStack = ((TileArtificersEnchanter) te).inputTopazHandler.getStackInSlot(0);
            ItemStack miscStack = ((TileArtificersEnchanter) te).inputMiscHandler.getStackInSlot(0);

            if (((!topazStack.isEmpty()) || (!miscStack.isEmpty())) && (player.experienceLevel >= 20 || player.isCreative())) {
                if ((topazStack.getItem().equals(Item.REGISTRY.getObject(Topaz.TOPAZ)))) {
                     if (player.getHeldItemMainhand().getItem() instanceof ItemBow) {
                         Map<Enchantment, Integer> enchantments =  EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.SPLINTERSHOT) == null && enchantments.get(EnchantmentInit.NETSHOT) == null) {
                             if (miscStack.getItem().equals(Item.getItemFromBlock(Blocks.CACTUS))) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.SPLINTERSHOT, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(Items.STRING)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.NETSHOT, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemSword) || (player.getHeldItemMainhand().getItem() instanceof ItemAxe)) {
                         Map<Enchantment, Integer> enchantments =  EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.SENDING) == null && enchantments.get(EnchantmentInit.EXECUTIONER) == null) {
                             if (miscStack.getItem().equals(Items.FEATHER)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.SENDING, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(Items.SHEARS)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.EXECUTIONER, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemPickaxe)) {
                         Map<Enchantment, Integer> enchantments =  EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.BORE) == null && enchantments.get(EnchantmentInit.OREINFUSER) == null) {
                             if (miscStack.getItem().equals(Items.NETHER_STAR)) {
                                 if (enchantments.get(Enchantments.FORTUNE) == null && enchantments.get(Enchantments.SILK_TOUCH) == null) {
                                     ItemStack newItem = player.getHeldItemMainhand();
                                     newItem.addEnchantment(EnchantmentInit.OREINFUSER, 1);
                                     player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                     world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                     world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                     if (!player.isCreative()) {
                                         player.addExperienceLevel(-20);
                                     }
                                     if (topazStack.getCount() == 1) {
                                         ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                     } else {
                                         topazStack.setCount(topazStack.getCount() - 1);
                                         ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                     }
                                     if (miscStack.getCount() == 1) {
                                         ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                     } else {
                                         miscStack.setCount(miscStack.getCount() - 1);
                                         ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                     }
                                 }
                             } else if (miscStack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.PISTON))) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.BORE, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemShears)) {
                         Map<Enchantment, Integer> enchantments =  EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.SCYTHING) == null && enchantments.get(EnchantmentInit.APPLEPICKER) == null) {
                             if (miscStack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.SAPLING))) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.APPLEPICKER, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(Items.WHEAT_SEEDS)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.SCYTHING, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemFishingRod)) {
                         Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.NETTING) == null && enchantments.get(Enchantments.KNOCKBACK) == null) {
                             if (miscStack.getItem().equals(Items.EMERALD)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.NETTING, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(Items.BLAZE_ROD)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(Enchantments.KNOCKBACK, 2);
                                 newItem.addEnchantment(Enchantments.SHARPNESS, 5);
                                 newItem.addEnchantment(Enchantments.FIRE_ASPECT, 2);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemHoe)) {
                         Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.ENRICHER) == null && enchantments.get(EnchantmentInit.TILLER) == null) {
                             if (miscStack.getItem().equals(Items.BONE)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.ENRICHER, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.DIRT))) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.TILLER, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemSpade)) {
                         Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.TREASUREFINDER) == null && enchantments.get(EnchantmentInit.PATHMAKER) == null) {
                             if (miscStack.getItem().equals(Items.DIAMOND)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.TREASUREFINDER, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(Items.EMERALD)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.PATHMAKER, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemArmor) && ((ItemArmor) player.getHeldItemMainhand().getItem()).armorType == EntityEquipmentSlot.FEET) {
                         Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.COBBLESTEP) == null && enchantments.get(EnchantmentInit.HEAVY) == null && enchantments.get(EnchantmentInit.LEAPOFFAITH) == null) {
                             if (miscStack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.NETHERRACK))) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.COBBLESTEP, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.IRON_BLOCK))) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.HEAVY, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(Items.FEATHER)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.LEAPOFFAITH, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemArmor) && ((ItemArmor) player.getHeldItemMainhand().getItem()).armorType == EntityEquipmentSlot.LEGS) {
                         Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.COMFORTABLEFIT) == null && enchantments.get(EnchantmentInit.BIONICS) == null) {
                             if (miscStack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.PISTON))) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.BIONICS, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.WOOL))) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.COMFORTABLEFIT, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemArmor) && ((ItemArmor) player.getHeldItemMainhand().getItem()).armorType == EntityEquipmentSlot.CHEST) {
                         Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.HOTMETAL) == null && enchantments.get(EnchantmentInit.COLDMETAL) == null) {
                             if (miscStack.getItem().equals(Items.BLAZE_POWDER)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.HOTMETAL, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(Items.SNOWBALL)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.COLDMETAL, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     } else if ((player.getHeldItemMainhand().getItem() instanceof ItemArmor) && ((ItemArmor) player.getHeldItemMainhand().getItem()).armorType == EntityEquipmentSlot.HEAD) {
                         Map<Enchantment, Integer> enchantments = EnchantmentHelper.getEnchantments(player.getHeldItemMainhand());
                         if (enchantments.get(EnchantmentInit.ARCANEHEADLAMP) == null && enchantments.get(EnchantmentInit.BALLOON) == null) {
                             if (miscStack.getItem().equals(ItemBlock.getItemFromBlock(Blocks.GLOWSTONE))) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.ARCANEHEADLAMP, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             } else if (miscStack.getItem().equals(Items.SHULKER_SHELL)) {
                                 ItemStack newItem = player.getHeldItemMainhand();
                                 newItem.addEnchantment(EnchantmentInit.BALLOON, 1);
                                 player.setHeldItem(EnumHand.MAIN_HAND, newItem);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 world.playSound((EntityPlayer) null, pos, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1, 1.0F);
                                 if (!player.isCreative()) {
                                     player.addExperienceLevel(-20);
                                 }
                                 if (topazStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     topazStack.setCount(topazStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputTopazHandler.setStackInSlot(0, topazStack);
                                 }
                                 if (miscStack.getCount() == 1) {
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, ItemStack.EMPTY);
                                 } else {
                                     miscStack.setCount(miscStack.getCount() - 1);
                                     ((TileArtificersEnchanter) te).inputMiscHandler.setStackInSlot(0, miscStack);
                                 }
                             }
                         }
                     }
                }
            }
        }
        return true;
    }
}
