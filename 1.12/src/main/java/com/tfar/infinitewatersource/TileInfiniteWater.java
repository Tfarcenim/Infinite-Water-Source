package com.tfar.infinitewatersource;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileInfiniteWater extends TileEntity {
  private static class WaterTank extends FluidTank {

    public WaterTank(int capacity) {
      super(capacity);
    }

    private static final FluidStack MAX_WATER = new FluidStack(FluidRegistry.WATER, Integer.MAX_VALUE);

    @Override
    public FluidStack getFluid() {
      return MAX_WATER;
    }

    @Override
    public int getFluidAmount() {
      return Integer.MAX_VALUE;
    }

    @Override
    public int getCapacity() {
      return Integer.MAX_VALUE;
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
      return 0;//resource.amount;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
      if (resource.getFluid() == FluidRegistry.WATER) {
        return resource.copy();
      }

      return super.drain(resource, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
      return new FluidStack(FluidRegistry.WATER, maxDrain);
    }
  }

  private final FluidTank waterTank = new WaterTank(16000);

  @Override
  @Nonnull
  public NBTTagCompound getUpdateTag() {
    return writeToNBT(new NBTTagCompound());
  }

  @Override
  public SPacketUpdateTileEntity getUpdatePacket() {
    return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
  }

  @Override
  public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
    super.onDataPacket(net, pkt);
    readFromNBT(pkt.getNbtCompound());
  }

  @Override
  @Nonnull
  public NBTTagCompound writeToNBT(NBTTagCompound tagCompound) {
    super.writeToNBT(tagCompound);
    waterTank.writeToNBT(tagCompound);
    return tagCompound;
  }

  @Override
  public void readFromNBT(NBTTagCompound tagCompound) {
    super.readFromNBT(tagCompound);
    waterTank.readFromNBT(tagCompound);
  }

  public int getWaterAmount() {
    return waterTank.getFluidAmount();
  }

  public int getWaterCapacity() {
    return waterTank.getCapacity();
  }

  @Override
  public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
    return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
            || super.hasCapability(capability, facing);
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
return (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) ? (T) waterTank : super.getCapability(capability, facing);
  }

  @Override
  public boolean shouldRefresh(World world, BlockPos pos,@Nonnull IBlockState oldState,@Nonnull IBlockState newSate) {
    return oldState.getBlock() != newSate.getBlock();
  }
}

