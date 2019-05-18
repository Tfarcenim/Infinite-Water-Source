package com.tfar.infinitewatersource;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class BlockInfiniteWater extends Block {

  public static final ResourceLocation registryName = new ResourceLocation(InfiniteWaterSource.MODID, InfiniteWaterSource.MODID);

  public BlockInfiniteWater() {
    super(Material.IRON);
    setTranslationKey(registryName.toString());
    setSoundType(SoundType.METAL);
    setHardness(5);
    setResistance(30);
  }

  @Override
  public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
    ItemStack itemHeld = player.getHeldItem(hand);
    TileEntity tank = world.getTileEntity(pos);
    if (tank != null) {
      IFluidHandler handler = tank.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

      if (handler != null) {
        PlayerInvWrapper invWrapper = new PlayerInvWrapper(player.inventory);

        FluidActionResult fillResult = FluidUtil.tryFillContainerAndStow(itemHeld, handler, invWrapper, Integer.MAX_VALUE, player, true);
        if (fillResult.isSuccess()) {
          player.setHeldItem(hand, fillResult.getResult());
          return true;
        }
      }
    }
    return false;
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(@Nonnull World world,@Nonnull IBlockState state) {
    return new TileInfiniteWater();
  }

  @SideOnly(Side.CLIENT)
  public void registerModel() {
    ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
  }

}
