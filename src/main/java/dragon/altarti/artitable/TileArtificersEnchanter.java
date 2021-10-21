package dragon.altarti.artitable;

import dragon.altarti.EnchantmentInit;
import dragon.altarti.items.Topaz;
import dragon.altarti.proxy.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

public class TileArtificersEnchanter extends TileEntity{
    public static final int SIZE = 2;

        /*if (((!topazStack.isEmpty()) || (!toolStack.isEmpty()) || (!itemStack.isEmpty()) || outputStack.isEmpty()) && ArtificersEnchanter.get)
        {
            if (!this.world.isRemote) {
                if ((topazStack.getItem().equals(Item.REGISTRY.getObject(Topaz.TOPAZ)))) {
                    if (toolStack.getItem().equals(Item.getIdFromItem(Items.BOW))) {
                        if (itemStack.getItem().equals(Item.getItemFromBlock(Blocks.CACTUS))) {
                            outputToolHandler.setStackInSlot(3, toolStack);
                            outputStack.addEnchantment(EnchantmentInit.SPLINTERSHOT, 1);
                        }
                    }
                }
            }
        }*/



                /*if (!list.isEmpty())
                {
                    playerIn.onEnchant(itemstack, i);
                    boolean flag = itemstack.getItem() == Items.BOOK;

                    if (flag)
                    {
                        itemstack = new ItemStack(Items.ENCHANTED_BOOK);
                        this.tableInventory.setInventorySlotContents(0, itemstack);
                    }

                    for (int j = 0; j < list.size(); ++j)
                    {
                        EnchantmentData enchantmentdata = list.get(j);

                        if (flag)
                        {
                            ItemEnchantedBook.addEnchantment(itemstack, enchantmentdata);
                        }
                        else
                        {
                            itemstack.addEnchantment(enchantmentdata.enchantment, enchantmentdata.enchantmentLevel);
                        }
                    }

                    if (!playerIn.capabilities.isCreativeMode)
                    {
                        itemstack1.shrink(i);

                        if (itemstack1.isEmpty())
                        {
                            this.tableInventory.setInventorySlotContents(1, ItemStack.EMPTY);
                        }
                    }

                    playerIn.addStat(StatList.ITEM_ENCHANTED);

                    if (playerIn instanceof EntityPlayerMP)
                    {
                        CriteriaTriggers.ENCHANTED_ITEM.trigger((EntityPlayerMP)playerIn, itemstack, i);
                    }

                    this.tableInventory.markDirty();
                    this.xpSeed = playerIn.getXPSeed();
                    this.onCraftMatrixChanged(this.tableInventory);
                    this.worldPointer.playSound((EntityPlayer)null, this.position, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.worldPointer.rand.nextFloat() * 0.1F + 0.9F);
                }*/


    //Detects whether items have been placed in the enchanter.
    public ItemStackHandler inputTopazHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileArtificersEnchanter.this.markDirty();
        }
    };

    public ItemStackHandler inputMiscHandler = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileArtificersEnchanter.this.markDirty();
        }
    };

    //Combines the four separate inputs/outputs.
    private CombinedInvWrapper combinedHandler = new CombinedInvWrapper(inputTopazHandler, inputMiscHandler);

    //These next two functions allow the enchanter to store items.
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("itemsTopazIn")) {
            inputTopazHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsTopazIn"));
        }
        if (compound.hasKey("itemsMiscIn")) {
            inputMiscHandler.deserializeNBT((NBTTagCompound) compound.getTag("itemsMiscIn"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("itemsTopazIn", inputTopazHandler.serializeNBT());
        compound.setTag("itemsMiscIn", inputMiscHandler.serializeNBT());
        return compound;
    }

    //Determines whether the player is close enough to interact with the enchanter.
    public boolean canInteractWith(EntityPlayer player) {
        return !isInvalid() && player.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    //Gets if the block can hold items.
    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    //Gets how the item can hold items. (Hopper usage.)
    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(combinedHandler);
            } else if (facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputTopazHandler);
            } else if (facing == EnumFacing.NORTH || facing == EnumFacing.SOUTH) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputMiscHandler);
            } else if (facing == EnumFacing.EAST || facing == EnumFacing.WEST) {
                return  CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputMiscHandler);
            }
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(inputTopazHandler);
        }
        return super.getCapability(capability, facing);
    }
}
