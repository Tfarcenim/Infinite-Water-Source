package com.tfar.infinitewatersource;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.items.wrapper.PlayerInvWrapper;

import javax.annotation.Nullable;

public class BlockInfiniteWater extends Block {

  public BlockInfiniteWater(Properties properties) {
    super(properties);
  }

  @Override
  public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult result) {
    ItemStack itemHeld = player.getHeldItem(hand);
    TileEntity tank = world.getTileEntity(pos);
    final boolean[] success = {false};
    if (tank != null) {
      tank.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).ifPresent( handler -> {
        PlayerInvWrapper invWrapper = new PlayerInvWrapper(player.inventory);
        FluidActionResult fillResult = FluidUtil.tryFillContainerAndStow(itemHeld, handler, invWrapper, Integer.MAX_VALUE, player, true);
        if (fillResult.isSuccess()) {
          player.setHeldItem(hand, fillResult.getResult());
          success[0] = true;
        }
      });
    }
    return success[0] ? ActionResultType.SUCCESS : ActionResultType.PASS;
  }

  @Override
  public boolean hasTileEntity(BlockState state) {
    return true;
  }

  @Nullable
  @Override
  public TileEntity createTileEntity(BlockState state, IBlockReader world) {
    return new TileInfiniteWater();
  }
}
