package com.tfar.infinitewatersource;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@Mod(modid = InfiniteWaterSource.MODID, name = InfiniteWaterSource.NAME, version = InfiniteWaterSource.VERSION)
@Mod.EventBusSubscriber
public class InfiniteWaterSource {
  public static final String MODID = "infinitewatersource";
  public static final String NAME = "Example Mod";
  public static final String VERSION = "1.0";

  @GameRegistry.ObjectHolder(MODID + ":" + MODID)
  public static BlockInfiniteWater infiniteWater;

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
  }

  @SubscribeEvent
  public static void registerBlock(RegistryEvent.Register<Block> e) {
    IForgeRegistry<Block> registry = e.getRegistry();
    BlockInfiniteWater infiniteWater = new BlockInfiniteWater();
    infiniteWater.setRegistryName(BlockInfiniteWater.name);
    infiniteWater.setCreativeTab(CreativeTabs.REDSTONE);
    registry.register(infiniteWater);
    GameRegistry.registerTileEntity(TileInfiniteWater.class, infiniteWater.getRegistryName());

  }

  @SubscribeEvent
  public static void registerItemBlock(RegistryEvent.Register<Item> e) {

    IForgeRegistry<Item> registry = e.getRegistry();
    registry.register(new ItemBlock(infiniteWater).setRegistryName(infiniteWater.getRegistryName()));
  }

  @SubscribeEvent
  public static void registerModel(ModelRegistryEvent e) {
    infiniteWater.registerModel();
  }
}
