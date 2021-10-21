package dragon.altarti;

import dragon.altarti.enchantments.*;
import dragon.altarti.util.Reference;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mod.EventBusSubscriber(modid = Reference.MODID)
public class EnchantmentInit {
    public static final List<Enchantment> ENCHANTMENTS = new ArrayList<Enchantment>();
    public static final Enchantment SPLINTERSHOT = new EnchantmentSplintershot();
    public static final Enchantment NETSHOT = new EnchantmentNetShot();
    public static final Enchantment APPLEPICKER = new EnchantmentApplePicker();
    public static final Enchantment ARCANEHEADLAMP = new EnchantmentArcaneHeadlamp();
    public static final Enchantment COLDMETAL = new EnchantmentColdMetal();
    public static final Enchantment COMFORTABLEFIT = new EnchantmentComfortableFit();
    public static final Enchantment SENDING = new EnchantmentSending();
    public static final Enchantment ENRICHER = new EnchantmentEnricher();
    public static final Enchantment EXECUTIONER = new EnchantmentExecutioner();
    public static final Enchantment HOTMETAL = new EnchantmentHotMetal();
    public static final Enchantment TREASUREFINDER = new EnchantmentTreasureFinder();
    public static final Enchantment BIONICS = new EnchantmentBionics();
    public static final Enchantment BORE = new EnchantmentBore();
    public static final Enchantment COBBLESTEP = new EnchantmentCobblestep();
    public static final Enchantment HEAVY = new EnchantmentHeavy();
    public static final Enchantment NETTING = new EnchantmentNetting();
    public static final Enchantment OREINFUSER = new EnchantmentOreInfuser();
    public static final Enchantment BALLOON = new EnchantmentBalloon();
    public static final Enchantment SCYTHING = new EnchantmentScything();
    public static final Enchantment TILLER = new EnchantmentTiller();
    public static final Enchantment PATHMAKER = new EnchantmentPathmaker();
    public static final Enchantment LEAPOFFAITH = new EnchantmentLeapOfFaith();

    @SubscribeEvent
    public static void leapoffaith(LivingFallEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(LEAPOFFAITH, living);
        BlockPos pos = living.getPosition();
        World world = event.getEntity().world;

        if (level > 0) {
            event.setDistance(0);
        }
    }

    @SubscribeEvent
    public static void netting(PlayerInteractEvent.RightClickItem event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(NETTING, living);

        if (level > 0) {
            living.addPotionEffect(new PotionEffect(Potion.getPotionById(26), 900, 0));
        }
    }

    @SubscribeEvent
    public static void applepicker(BlockEvent.BreakEvent event) {
        int level = EnchantmentHelper.getMaxEnchantmentLevel(APPLEPICKER, event.getPlayer());

        if (level > 0) {
            if (event.getWorld().getBlockState(event.getPos()) == Blocks.LEAVES.getDefaultState()) {
                Random rand = new Random();
                if (rand.nextInt(4) != 0) {
                    event.getPlayer().addItemStackToInventory(new ItemStack(Items.APPLE));
                }
            }
        }
    }

    @SubscribeEvent
    public static void arcaneheadlamp(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(ARCANEHEADLAMP, living);
        BlockPos pos = living.getPosition();
        World world = event.getEntity().world;

        if (level > 0) {
            if (world.getLight(pos) < 7 && pos.getY() < 63) {
                world.setBlockState(new BlockPos(pos.getX(), pos.getY()+4, pos.getZ()), Blocks.GLOWSTONE.getDefaultState());
            }
        }
    }


    @SubscribeEvent
    public static void comfortablefit(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(COMFORTABLEFIT, living);
        BlockPos pos = living.getPosition();
        World world = event.getEntity().world;

        if (level > 0) {
            living.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 60, 1));
            if (living.getActivePotionEffect(Potion.getPotionById(2)) != null) {
                living.removeActivePotionEffect(Potion.getPotionById(2));
            }
        }
    }

    @SubscribeEvent
    public static void sending(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(SENDING, living);
        BlockPos pos = living.getPosition();
        World world = event.getEntity().world;

        if (level > 0) {
            if (living.isSwingInProgress) {
                living.getHeldItemMainhand().damageItem(1, living);
                int rotation = MathHelper.floor((double) (living.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
                float pitch = living.rotationPitch;
                if (pitch <= -30 && pitch >= -90) {
                    living.addVelocity(0.0, 0.40, 0.0);
                } else if (pitch >= 50 && pitch <= 90) {
                    living.addVelocity(0.0, -0.40, 0.0);
                }else {
                    switch (rotation) {
                        case 0:
                            living.addVelocity(0.0, 0.1, 0.4);
                            break;
                        case 1:
                            living.addVelocity(-0.4, 0.1, 0.0);
                            break;
                        case 2:
                            living.addVelocity(0.0, 0.1, -0.4);
                            break;
                        case 3:
                            living.addVelocity(0.4, 0.1, 0.0);
                            break;
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void enricher(PlayerInteractEvent.RightClickBlock event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(ENRICHER, living);
        BlockPos pos = event.getPos();

        if (level > 0) {
            if (event.getWorld().getBlockState(pos) == Blocks.GRASS.getDefaultState() || event.getWorld().getBlockState(pos) == Blocks.DIRT.getDefaultState()) {
                event.getItemStack().damageItem(1, event.getEntityLiving());
                event.getWorld().setBlockState(pos, Blocks.FARMLAND.getDefaultState());
                Random rand = new Random();
                if (rand.nextInt(5) == 0) {
                    event.getEntityPlayer().addItemStackToInventory(new ItemStack(Item.getItemFromBlock(Blocks.BONE_BLOCK)));
                }
            }
        }
    }

    @SubscribeEvent
    public static void treasurefinder(BlockEvent.BreakEvent event) {
        EntityLivingBase living = event.getPlayer();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(TREASUREFINDER, living);
        BlockPos pos = event.getPos();

        if (level > 0) {
            if (event.getWorld().getBlockState(pos) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(pos) == Blocks.GRASS.getDefaultState()) {
                Random rand = new Random();
                if (rand.nextInt(10) == 0) {
                    int item = rand.nextInt(100);
                    if (item < 65) {
                        event.getPlayer().addItemStackToInventory(new ItemStack(Items.COAL));
                    } else if (item < 85) {
                        event.getPlayer().addItemStackToInventory(new ItemStack(Items.IRON_INGOT));
                    } else if (item < 95) {
                        event.getPlayer().addItemStackToInventory(new ItemStack(Items.GOLD_INGOT));
                    } else if (item < 98) {
                        event.getPlayer().addItemStackToInventory(new ItemStack(Items.DIAMOND));
                    } else {
                        event.getPlayer().addItemStackToInventory(new ItemStack(Items.EMERALD));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void bionics(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(BIONICS, living);
        BlockPos pos = living.getPosition();
        World world = event.getEntity().world;

        if (level > 0) {
            living.addPotionEffect(new PotionEffect(Potion.getPotionById(8), 60, 2));
        }
    }

    @SubscribeEvent
    public static void bore(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(BORE, living);

        if (level > 0) {
            living.addPotionEffect(new PotionEffect(Potion.getPotionById(3), 40, 3));
        }
    }

    @SubscribeEvent
    public static void cobblestep(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(COBBLESTEP, living);
        BlockPos pos = living.getPosition();
        World world = event.getEntity().world;

        if (level > 0) {
            if (living.onGround) {
                float f = (float) Math.min(16, 2 + level);
                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);

                for (BlockPos.MutableBlockPos blockpos$mutableblockpos1 : BlockPos.getAllInBoxMutable(pos.add((double) (-f), -1.0D, (double) (-f)), pos.add((double) f, -1.0D, (double) f))) {
                    if (blockpos$mutableblockpos1.distanceSqToCenter(living.posX, living.posY, living.posZ) <= (double) (f * f)) {
                        blockpos$mutableblockpos.setPos(blockpos$mutableblockpos1.getX(), blockpos$mutableblockpos1.getY() + 1, blockpos$mutableblockpos1.getZ());
                        IBlockState iblockstate = world.getBlockState(blockpos$mutableblockpos);

                        if (iblockstate.getMaterial() == Material.AIR) {
                            IBlockState iblockstate1 = world.getBlockState(blockpos$mutableblockpos1);

                            if (iblockstate1.getMaterial() == Material.LAVA && (iblockstate1.getBlock() == Blocks.LAVA || iblockstate1.getBlock() == Blocks.FLOWING_LAVA) && ((Integer) iblockstate1.getValue(BlockLiquid.LEVEL)).intValue() == 0 && world.mayPlace(Blocks.COBBLESTONE, blockpos$mutableblockpos1, false, EnumFacing.DOWN, (Entity) null)) {
                                world.setBlockState(blockpos$mutableblockpos1, Blocks.COBBLESTONE.getDefaultState());
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void heavy(LivingEvent.LivingUpdateEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(HEAVY, living);
        BlockPos pos = living.getPosition();
        World world = event.getEntity().world;

        if (level > 0) {
            if (living.getActivePotionEffect(Potion.getPotionById(25)) != null) {
                living.removeActivePotionEffect(Potion.getPotionById(25));
            }
        }
    }

    @SubscribeEvent
    public static void oreinfuser(BlockEvent.BreakEvent event) {
        EntityLivingBase living = (EntityLivingBase) event.getPlayer();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(OREINFUSER, living);

        if (level > 0) {
            if (event.getWorld().getBlockState(event.getPos()) == Blocks.DIAMOND_ORE.getDefaultState()) {
                event.getPlayer().addItemStackToInventory(new ItemStack(Items.DIAMOND, 8));
            } else if (event.getWorld().getBlockState(event.getPos()) == Blocks.EMERALD_ORE.getDefaultState()) {
                event.getPlayer().addItemStackToInventory(new ItemStack(Items.EMERALD, 8));
            } else if (event.getWorld().getBlockState(event.getPos()) == Blocks.COAL_ORE.getDefaultState()) {
                event.getPlayer().addItemStackToInventory(new ItemStack(Items.COAL, 8));
            } else if (event.getWorld().getBlockState(event.getPos()) == Blocks.REDSTONE_ORE.getDefaultState()) {
                event.getPlayer().addItemStackToInventory(new ItemStack(Items.REDSTONE, 8));
            } else if (event.getWorld().getBlockState(event.getPos()) == Blocks.QUARTZ_ORE.getDefaultState()) {
                event.getPlayer().addItemStackToInventory(new ItemStack(Items.QUARTZ, 8));
            }  else if (event.getWorld().getBlockState(event.getPos()) == Blocks.LAPIS_ORE.getDefaultState()) {
                event.getPlayer().addItemStackToInventory(new ItemStack(Items.DYE, 8, 4));
            }
        }
    }

    @SubscribeEvent
    public static void balloon(LivingEvent.LivingJumpEvent event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(BALLOON, living);

        if (level > 0) {
            if (living.isSneaking()) {
                living.addPotionEffect(new PotionEffect(Potion.getPotionById(25), 100, 0));
            }
        }
    }

    @SubscribeEvent
    public static void scything(BlockEvent.BreakEvent event) {
        int level = EnchantmentHelper.getMaxEnchantmentLevel(SCYTHING, event.getPlayer());

        if (level > 0) {
            if (event.getWorld().getBlockState(event.getPos()) == Blocks.TALLGRASS.getPlant(event.getWorld(), event.getPos())) {
                Random rand = new Random();
                if (rand.nextInt(4) != 0) {
                    event.getPlayer().addItemStackToInventory(new ItemStack(Items.WHEAT));
                }
            }
        }
    }

    @SubscribeEvent
    public static void tiller(PlayerInteractEvent.RightClickBlock event) {
        EntityLivingBase living = event.getEntityLiving();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(TILLER, living);
        BlockPos pos = event.getPos();

        if ((event.getWorld().getBlockState(pos) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(pos) == Blocks.GRASS.getDefaultState()) && level > 0) {
            event.getWorld().setBlockState(pos, Blocks.FARMLAND.getDefaultState());
            if (event.getWorld().getBlockState(new BlockPos(pos.getX()+1, pos.getY(), pos.getZ()+1)) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(new BlockPos(pos.getX()+1, pos.getY(), pos.getZ()+1)) == Blocks.GRASS.getDefaultState()) {
                event.getWorld().setBlockState(new BlockPos(pos.getX()+1, pos.getY(), pos.getZ()+1), Blocks.FARMLAND.getDefaultState());
            }
            if (event.getWorld().getBlockState(new BlockPos(pos.getX()+1, pos.getY(), pos.getZ())) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(new BlockPos(pos.getX()+1, pos.getY(), pos.getZ())) == Blocks.GRASS.getDefaultState()) {
                event.getWorld().setBlockState(new BlockPos(pos.getX()+1, pos.getY(), pos.getZ()), Blocks.FARMLAND.getDefaultState());
            }
            if (event.getWorld().getBlockState(new BlockPos(pos.getX()+1, pos.getY(), pos.getZ()-1)) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(new BlockPos(pos.getX()+1, pos.getY(), pos.getZ()-1)) == Blocks.GRASS.getDefaultState()) {
                event.getWorld().setBlockState(new BlockPos(pos.getX()+1, pos.getY(), pos.getZ()-1), Blocks.FARMLAND.getDefaultState());
            }
            if (event.getWorld().getBlockState(new BlockPos(pos.getX()-1, pos.getY(), pos.getZ()+1)) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(new BlockPos(pos.getX()-1, pos.getY(), pos.getZ()+1)) == Blocks.GRASS.getDefaultState()) {
                event.getWorld().setBlockState(new BlockPos(pos.getX()-1, pos.getY(), pos.getZ()+1), Blocks.FARMLAND.getDefaultState());
            }
            if (event.getWorld().getBlockState(new BlockPos(pos.getX()-1, pos.getY(), pos.getZ())) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(new BlockPos(pos.getX()-1, pos.getY(), pos.getZ())) == Blocks.GRASS.getDefaultState()) {
                event.getWorld().setBlockState(new BlockPos(pos.getX()-1, pos.getY(), pos.getZ()), Blocks.FARMLAND.getDefaultState());
            }
            if (event.getWorld().getBlockState(new BlockPos(pos.getX()-1, pos.getY(), pos.getZ()-1)) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(new BlockPos(pos.getX()-1, pos.getY(), pos.getZ()-1)) == Blocks.GRASS.getDefaultState()) {
                event.getWorld().setBlockState(new BlockPos(pos.getX()-1, pos.getY(), pos.getZ()-1), Blocks.FARMLAND.getDefaultState());
            }
            if (event.getWorld().getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()+1)) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()+1)) == Blocks.GRASS.getDefaultState()) {
                event.getWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()+1), Blocks.FARMLAND.getDefaultState());
            }
            if (event.getWorld().getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()-1)) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()-1)) == Blocks.GRASS.getDefaultState()) {
                event.getWorld().setBlockState(new BlockPos(pos.getX(), pos.getY(), pos.getZ()-1), Blocks.FARMLAND.getDefaultState());
            }
            event.getItemStack().damageItem(3, event.getEntityLiving());
        }
    }

    @SubscribeEvent
    public static void pathmaker(BlockEvent.BreakEvent event) {
        EntityLivingBase living = event.getPlayer();
        int level = EnchantmentHelper.getMaxEnchantmentLevel(PATHMAKER, living);
        BlockPos pos = event.getPos();

        if ((event.getWorld().getBlockState(pos) == Blocks.DIRT.getDefaultState() || event.getWorld().getBlockState(pos) == Blocks.GRASS.getDefaultState()) && level > 0) {
            Random rand = new Random();
            if (rand.nextInt(250) == 0) {
                event.getPlayer().addItemStackToInventory(new ItemStack(Items.NETHER_STAR));
            }
        }
    }
}
