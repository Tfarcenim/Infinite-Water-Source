package com.tfar.infinitewatersource;

import net.minecraft.fluid.Fluids;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class TileInfiniteWater extends TileEntity {

	private final FluidTank waterTank = new WaterTank(Integer.MAX_VALUE);
	private final LazyOptional<IFluidHandler> optional = LazyOptional.of(() -> waterTank);

	public TileInfiniteWater() {
		super(InfiniteWaterSource.infiniteWaterTile);
		waterTank.setFluid(new FluidStack(Fluids.WATER, Integer.MAX_VALUE));
	}



	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
		return cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY ? optional.cast() : super.getCapability(cap);
	}
}

