package com.tfar.infinitewatersource;

import net.minecraft.fluid.Fluids;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.templates.FluidTank;

import javax.annotation.Nonnull;

public class WaterTank extends FluidTank {

	public WaterTank(int capacity) {
		super(capacity);
	}

	@Override
	public int fill(FluidStack resource, FluidAction action) {
		return 0;//no
	}

	@Nonnull
	@Override
	public FluidStack drain(FluidStack resource, FluidAction action) {
		if (resource.getFluid() == Fluids.WATER) {
			return resource.copy();
		}
		return super.drain(resource, action);
	}

	@Nonnull
	@Override
	public FluidStack drain(int maxDrain, FluidAction action) {
		return new FluidStack(Fluids.WATER, maxDrain);
	}
}
